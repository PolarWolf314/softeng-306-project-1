package nz.ac.aucklanduni.se306project1.activities;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;

import nz.ac.aucklanduni.se306project1.viewmodels.TopBarViewModel;

public abstract class TopBarActivity extends AppCompatActivity {

    protected TopBarViewModel topBarViewModel;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.topBarViewModel = new ViewModelProvider(this).get(TopBarViewModel.class);
        this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public boolean dispatchTouchEvent(final MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            final View view = this.getCurrentFocus();
            if (view instanceof SearchView.SearchAutoComplete) {
                // We can get the actual search view by navigating the XML tree... Yes this is cursed.
                final SearchView searchView = (SearchView) view.getParent().getParent().getParent().getParent();
                if (searchView.getQuery().toString().trim().isEmpty()) {
                    final Rect outRect = new Rect();
                    searchView.getGlobalVisibleRect(outRect);
                    if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                        this.topBarViewModel.setSearchBarExpanded(false);
                    }
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
}
