package nz.ac.aucklanduni.se306project1.iconbuttons;

import android.app.Activity;

import java.util.function.BiConsumer;

import nz.ac.aucklanduni.se306project1.R;
import nz.ac.aucklanduni.se306project1.viewmodels.TopBarViewModel;

public class WatchlistButton implements IconButton {

    private final boolean isInWatchlist;
    private final Runnable callback;

    public WatchlistButton(final boolean isInWatchlist, final Runnable callback) {
        this.isInWatchlist = isInWatchlist;
        this.callback = callback;
    }


    @Override
    public int getIconId() {
        if (this.isInWatchlist) {
            return R.drawable.favourite_solid_24dp;
        } else {
            return R.drawable.favourite_outline_24dp;
        }
    }

    @Override
    public BiConsumer<Activity, TopBarViewModel> getOnClickListener() {
        return (activity, topBarViewModel) -> this.callback.run();
    }
}
