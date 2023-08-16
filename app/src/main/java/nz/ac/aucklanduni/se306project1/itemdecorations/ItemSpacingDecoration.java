package nz.ac.aucklanduni.se306project1.itemdecorations;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemSpacingDecoration extends RecyclerView.ItemDecoration {
    private final int horizontalSpacing;

    public ItemSpacingDecoration(final Context context, final int horizontalSpacing) {
        this.horizontalSpacing = horizontalSpacing;
    }

    @Override
    public void getItemOffsets(@NonNull final Rect outRect, @NonNull final View view, @NonNull final RecyclerView parent, @NonNull final RecyclerView.State state) {
        outRect.left = this.horizontalSpacing;
        outRect.right = this.horizontalSpacing;

        // No vertical spacing
        outRect.bottom = 0;
        outRect.top = 0;

        // Apply spacing to all items, including the first one
    }
}

