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
    }

    @Override
    public boolean dispatchTouchEvent(final MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            final View view = this.getCurrentFocus();
            // The actual search view is contained somewhere internally so we can't check the id to
            // see if it matches our specific search view. That's fine for now though as we only
            // have one.
            if (view instanceof SearchView.SearchAutoComplete) {
                // We can get the actual search view by navigating the XML tree... Yes this is cursed.
                final View searchView = (View) view.getParent().getParent().getParent().getParent();
                final Rect outRect = new Rect();
                searchView.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    this.topBarViewModel.setSearchBarExpanded(false);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
}
