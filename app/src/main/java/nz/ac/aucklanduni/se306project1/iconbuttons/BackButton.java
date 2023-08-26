package nz.ac.aucklanduni.se306project1.iconbuttons;

import android.app.Activity;

import java.util.function.BiConsumer;

import nz.ac.aucklanduni.se306project1.R;
import nz.ac.aucklanduni.se306project1.viewmodels.TopBarViewModel;

public class BackButton implements IconButton {

    @Override
    public int getIconId() {
        return R.drawable.back_arrow_24dp;
    }

    @Override
    public BiConsumer<Activity, TopBarViewModel> getOnClickListener() {
        return (activity, topBarViewModel) -> activity.finish();
    }
}
