package nz.ac.aucklanduni.se306project1.itemdecorations;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VerticalItemSpacingDecoration extends RecyclerView.ItemDecoration {

    private final int verticalSpacing;

    public VerticalItemSpacingDecoration(final Context context, final int verticalSpacing) {
        this.verticalSpacing = verticalSpacing;
    }

    @Override
    public void getItemOffsets(@NonNull final Rect outRect, @NonNull final View view, @NonNull final RecyclerView parent, @NonNull final RecyclerView.State state) {
        final int position = parent.getChildAdapterPosition(view);

        if (position >= 0) {
            outRect.bottom = 2 * this.verticalSpacing;
        }
    }
}
