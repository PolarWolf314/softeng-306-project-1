package nz.ac.aucklanduni.se306project1.itemdecorations;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VerticalItemSpacingDecoration extends RecyclerView.ItemDecoration {

    private final int verticalSpacingPx;

    public VerticalItemSpacingDecoration(final Context context, final int verticalSpacingDp) {

        final float pixelDensity = context.getResources().getDisplayMetrics().density;
        this.verticalSpacingPx = Math.round(verticalSpacingDp * pixelDensity);
    }

    @Override
    public void getItemOffsets(@NonNull final Rect outRect, @NonNull final View view, @NonNull final RecyclerView parent, @NonNull final RecyclerView.State state) {
        final int position = parent.getChildAdapterPosition(view);

        if (position > 0) {
            outRect.top = this.verticalSpacingPx; // After you've converted the input from dp to px
        }
    }
}
