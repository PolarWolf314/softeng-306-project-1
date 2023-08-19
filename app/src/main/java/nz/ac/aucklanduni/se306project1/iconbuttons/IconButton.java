package nz.ac.aucklanduni.se306project1.iconbuttons;

import android.content.Context;

import androidx.annotation.DrawableRes;

import java.util.function.BiConsumer;

import nz.ac.aucklanduni.se306project1.viewmodels.TopBarViewModel;

public interface IconButton {
    /**
     * Retrieve the id of the drawable to use as the icon for this button as specified in
     * <code>R.drawable.ICON_NAME</code>.
     *
     * @return The id of the drawable to use for the icon
     */
    @DrawableRes
    int getIconId();

    /**
     * Retrieve the listener to use for clicks on the icon button. It will accept an instance of a
     * {@link Context} and {@link TopBarViewModel}.
     *
     * @return The icon buttons on click handler
     */
    BiConsumer<Context, TopBarViewModel> getOnClickListener();
}
