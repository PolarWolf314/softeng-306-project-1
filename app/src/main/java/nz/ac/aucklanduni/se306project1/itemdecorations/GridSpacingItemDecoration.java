package nz.ac.aucklanduni.se306project1.itemdecorations;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private final int numColumns;
    private final int spacingPx;

    public GridSpacingItemDecoration(final Context context, final int spanCount, final int spacingDp) {
        this.numColumns = spanCount;
        this.spacingPx = Math.round(spacingDp * context.getResources().getDisplayMetrics().density);
    }

    /**
     * Implementation modified from <a href="https://stackoverflow.com/a/30701422">here</a>.
     * <p>
     * {@inheritDoc}
     */
    @Override
    public void getItemOffsets(
            final Rect outRect,
            @NonNull final View view,
            final RecyclerView parent,
            @NonNull final RecyclerView.State state
    ) {
        int position = parent.getChildAdapterPosition(view); // item position
        int column = position % numColumns; // item column

        outRect.left = column * spacingPx / numColumns; // column * ((1f / spanCount) * spacing)
        outRect.right = spacingPx - (column + 1) * spacingPx / numColumns; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
        if (position >= numColumns) {
            outRect.top = spacingPx; // item top
        }
    }
}