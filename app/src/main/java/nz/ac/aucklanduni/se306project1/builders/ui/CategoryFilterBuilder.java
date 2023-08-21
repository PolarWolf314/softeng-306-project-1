package nz.ac.aucklanduni.se306project1.builders.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import nz.ac.aucklanduni.se306project1.viewmodels.ItemSearchViewModel;

public interface CategoryFilterBuilder {

    /**
     * Constructs the view which will manage the category-specific filtering. The created UI will
     * be injected into the top bar. Filtering can be achieved by adding filters to the provided
     * {@link ItemSearchViewModel}.
     *
     * @param context         The {@link Context} from the using activity
     * @param inflater        The {@link LayoutInflater}
     * @param searchViewModel The {@link ItemSearchViewModel} to use for applying filters to the
     *                        current items
     * @return The category-specific ui to inject into the top bar
     */
    View buildFilteringView(
            Context context, LayoutInflater inflater, ItemSearchViewModel searchViewModel);
}
