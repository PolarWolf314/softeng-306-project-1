package nz.ac.aucklanduni.se306project1.viewholders;

import android.content.Context;
import android.view.View;

import androidx.annotation.LayoutRes;

public interface ViewHolderBuilder<ViewHolder extends BindableViewHolder<?>> {

    /**
     * Retrieve the id of the layout for this particular view holder, as specified in
     * <code>R.layout.LAYOUT_NAME</code>.
     *
     * @return The id of the layout
     */
    @LayoutRes
    int getLayoutId();

    /**
     * Constructs an instance of {@link ViewHolder} with the given {@link Context} and {@link View}.
     * This will typically  be as simple as passing these parameters to the constructor of the
     * view holder.
     *
     * @param context The {@link Context} of the current activity
     * @param view    The view to create the {@link ViewHolder} with
     * @return The constructed view holder instance
     */
    ViewHolder createViewHolder(final Context context, final View view);
}
