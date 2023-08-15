package nz.ac.aucklanduni.se306project1.viewholders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BindableViewHolder<Item> extends RecyclerView.ViewHolder {
    public BindableViewHolder(@NonNull final View itemView) {
        super(itemView);
    }

    /**
     * Populate the data in the views contained within this view holder using the provided {@link Item}.
     *
     * @param item The {@link Item} to bind to the view holder
     */
    public abstract void bindFrom(final Item item);
}
