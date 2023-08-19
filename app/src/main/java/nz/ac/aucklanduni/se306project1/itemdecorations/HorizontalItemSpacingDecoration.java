package nz.ac.aucklanduni.se306project1.itemdecorations;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HorizontalItemSpacingDecoration extends RecyclerView.ItemDecoration {
    private final int horizontalSpacing;

    public HorizontalItemSpacingDecoration(final Context context, final int horizontalSpacing) {
        this.horizontalSpacing = horizontalSpacing;
    }

    @Override
    public void getItemOffsets(@NonNull final Rect outRect, @NonNull final View view, @NonNull final RecyclerView parent, @NonNull final RecyclerView.State state) {
        final int position = parent.getChildAdapterPosition(view);

        if (position != 0) {
            outRect.left = 2 * this.horizontalSpacing;
        }

        // No vertical spacing
        outRect.bottom = 0;
        outRect.top = 0;
    }
}


