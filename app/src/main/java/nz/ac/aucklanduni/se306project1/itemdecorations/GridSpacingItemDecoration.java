package nz.ac.aucklanduni.se306project1.itemdecorations;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private final int numColumns;
    private final int verticalSpacingPx;
    private final int horizontalSpacingPx;

    public GridSpacingItemDecoration(
            final Context context,
            final int spanCount,
            final int verticalSpacingDp,
            final int horizontalSpacingDp
    ) {
        final float pixelDensity = context.getResources().getDisplayMetrics().density;

        this.numColumns = spanCount;
        this.verticalSpacingPx = Math.round(verticalSpacingDp * pixelDensity);
        this.horizontalSpacingPx = Math.round(horizontalSpacingDp * pixelDensity);
    }

    /**
     * Attaches an instance of {@link GridLayoutManager} and {@link GridSpacingItemDecoration} to
     * the recycler view with the specified number of columns and spacing (In dp) between them.
     *
     * @param recyclerView        The {@link RecyclerView} to attach the grid to
     * @param context             The {@link Context}
     * @param numColumns          The number of columns in the grid
     * @param verticalSpacingDp   The spacing in dp between rows
     * @param horizontalSpacingDp The spacing in dp between columns
     */
    public static void attachGrid(
            final RecyclerView recyclerView,
            final Context context,
            final int numColumns,
            final int verticalSpacingDp,
            final int horizontalSpacingDp
    ) {

        recyclerView.addItemDecoration(new GridSpacingItemDecoration(
                context, numColumns, verticalSpacingDp, horizontalSpacingDp));
        recyclerView.setLayoutManager(new GridLayoutManager(context, numColumns));
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
        final int position = parent.getChildAdapterPosition(view); // item position
        final int column = position % this.numColumns; // item column

        outRect.left = column * this.horizontalSpacingPx / this.numColumns; // column * ((1f / spanCount) * spacing)
        outRect.right = this.horizontalSpacingPx - (column + 1) * this.horizontalSpacingPx / this.numColumns; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
        if (position >= this.numColumns) {
            outRect.top = this.verticalSpacingPx; // item top
        }
    }
}