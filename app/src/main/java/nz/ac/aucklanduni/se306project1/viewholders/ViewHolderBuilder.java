package nz.ac.aucklanduni.se306project1.viewholders;

import android.view.View;

import androidx.annotation.LayoutRes;

public interface ViewHolderBuilder<ViewHolder extends BindableViewHolder<?>> {

    /**
     * Retrieve the id of the layout for this particular view holder, as specified in
     * <link>R.layout.LAYOUT_NAME</code>.
     *
     * @return The id of the layout
     */
    @LayoutRes
    int getLayoutId();

    /**
     * Constructs an instance of {@link ViewHolder} with the given {@link View}. This will typically
     * be as simple as passing the view to the constructor of the view holder.
     *
     * @param view The view to create the {@link ViewHolder} with
     * @return The constructed view holder instance
     */
    ViewHolder createViewHolder(final View view);
}
