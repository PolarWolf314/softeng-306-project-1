package nz.ac.aucklanduni.se306project1.iconbuttons;

import android.app.Activity;

import java.util.function.BiConsumer;

import nz.ac.aucklanduni.se306project1.R;
import nz.ac.aucklanduni.se306project1.viewmodels.TopBarViewModel;

public class SearchIcon implements IconButton {

    public static final SearchIcon INSTANCE = new SearchIcon();

    @Override
    public int getIconId() {
        return R.drawable.search_icon_24dp;
    }

    @Override
    public BiConsumer<Activity, TopBarViewModel> getOnClickListener() {
        return (activity, topBarViewModel) -> topBarViewModel.setSearchBarExpanded(true);
    }
}
