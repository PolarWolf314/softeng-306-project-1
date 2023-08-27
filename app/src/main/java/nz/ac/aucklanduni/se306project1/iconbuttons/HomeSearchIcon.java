package nz.ac.aucklanduni.se306project1.iconbuttons;

import android.app.Activity;
import android.content.Intent;

import java.util.function.BiConsumer;

import nz.ac.aucklanduni.se306project1.activities.ListActivity;
import nz.ac.aucklanduni.se306project1.data.Constants;
import nz.ac.aucklanduni.se306project1.models.enums.Category;
import nz.ac.aucklanduni.se306project1.viewmodels.TopBarViewModel;

public class HomeSearchIcon extends SearchIcon {
    @Override
    public BiConsumer<Activity, TopBarViewModel> getOnClickListener() {
        return (activity, topBarViewModel) -> {
            final Intent intent = new Intent(activity, ListActivity.class);
            intent.putExtra(Constants.IntentKeys.CATEGORY_ID, Category.ALL_ITEMS.getId());
            activity.startActivity(intent);
        };
    }
}
