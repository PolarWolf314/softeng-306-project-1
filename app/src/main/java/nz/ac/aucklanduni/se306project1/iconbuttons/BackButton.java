package nz.ac.aucklanduni.se306project1.iconbuttons;

import android.content.Context;
import android.content.Intent;

import java.util.function.BiConsumer;

import nz.ac.aucklanduni.se306project1.R;
import nz.ac.aucklanduni.se306project1.viewmodels.TopBarViewModel;

public class BackButton implements IconButton {

    private final Intent destinationIntent;

    public BackButton(final Intent destinationIntent) {
        this.destinationIntent = destinationIntent;
    }

    @Override
    public int getIconId() {
        return R.drawable.back_arrow_24dp;
    }

    @Override
    public BiConsumer<Context, TopBarViewModel> getOnClickListener() {
        return (context, topBarViewModel) -> context.startActivity(this.destinationIntent);
    }
}
