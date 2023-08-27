package nz.ac.aucklanduni.se306project1.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import nz.ac.aucklanduni.se306project1.R;
import nz.ac.aucklanduni.se306project1.data.Constants;
import nz.ac.aucklanduni.se306project1.databinding.SubcategoryChipBinding;
import nz.ac.aucklanduni.se306project1.models.Subcategory;
import nz.ac.aucklanduni.se306project1.viewmodels.ItemSearchViewModel;

public class SubcategoryFilterBuilder<Item> implements CategoryFilterBuilder {

    private final Class<Item> itemClass;
    private final Subcategory[] subcategories;
    private final Function<Item, Subcategory> subcategoryExtractor;

    public SubcategoryFilterBuilder(
            final Class<Item> itemClass,
            final Subcategory[] subcategories,
            final Function<Item, Subcategory> subcategoryExtractor
    ) {
        this.itemClass = itemClass;
        this.subcategories = subcategories;
        this.subcategoryExtractor = subcategoryExtractor;
    }

    private ChipGroup buildChipGroup(final Context context) {
        final HorizontalScrollView horizontalScrollView = new HorizontalScrollView(context);
        final ChipGroup chipGroup = new ChipGroup(context);
        final int horizontalPadding = context.getResources().getDimensionPixelSize(R.dimen.top_bar_horizontal_padding);

        horizontalScrollView.addView(chipGroup);
        horizontalScrollView.setHorizontalScrollBarEnabled(false);
        chipGroup.setSingleLine(true);
        chipGroup.setPadding(horizontalPadding, 0, horizontalPadding, 0);

        return chipGroup;
    }

    @Override
    public View buildFilteringView(
            final Context context,
            final LayoutInflater inflater,
            final ItemSearchViewModel searchViewModel
    ) {
        final ChipGroup chipGroup = this.buildChipGroup(context);

        final Set<Subcategory> selectedSubcategories = new HashSet<>();
        for (final Subcategory subcategory : this.subcategories) {
            final Chip chip = SubcategoryChipBinding.inflate(inflater).getRoot();
            chip.setText(subcategory.getDisplayNameId());
            chip.setOnCheckedChangeListener((button, isChecked) -> {
                this.updateSelectedSubcategories(isChecked, subcategory, selectedSubcategories);
                this.onCheckedChange(selectedSubcategories, searchViewModel);
            });

            chipGroup.addView(chip);
        }

        return chipGroup.getRootView();
    }

    private void updateSelectedSubcategories(
            final boolean isChecked,
            final Subcategory subcategory,
            final Set<Subcategory> selectedSubcategories
    ) {
        if (isChecked) selectedSubcategories.add(subcategory);
        else selectedSubcategories.remove(subcategory);
    }

    private void onCheckedChange(
            final Set<Subcategory> selectedSubcategories,
            final ItemSearchViewModel searchViewModel
    ) {
        if (selectedSubcategories.isEmpty()) {
            searchViewModel.removeFilter(Constants.FilterKeys.CATEGORY_FILTERING);
        } else {
            searchViewModel.putFilter(Constants.FilterKeys.CATEGORY_FILTERING,
                    (item) -> {
                        if (!this.itemClass.isAssignableFrom(item.getClass())) {
                            return false;
                        }

                        final Subcategory itemSubcategory = this.subcategoryExtractor.apply(this.itemClass.cast(item));
                        return selectedSubcategories.contains(itemSubcategory);
                    });

        }
    }
}
