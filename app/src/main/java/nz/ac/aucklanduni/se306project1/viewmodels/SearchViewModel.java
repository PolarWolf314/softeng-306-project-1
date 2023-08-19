package nz.ac.aucklanduni.se306project1.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class SearchViewModel<Item> extends ViewModel {
    private final List<Predicate<Item>> filters = new ArrayList<>();
    private final MutableLiveData<List<Item>> filteredItems = new MutableLiveData<>();
    private List<Item> originalItems = Collections.emptyList();

    /**
     * Removes this filter from the list of filters being applied to the items. This will cause the
     * filtered items to be recalculated.
     *
     * @param filter The filter to remove
     */
    public void removeFilter(final Predicate<Item> filter) {
        if (this.filters.remove(filter)) {
            this.applyFilters();
        }
    }

    /**
     * Adds this filter to the list of filters being applied to the items. This will cause the
     * filtered items to be recalculated.
     *
     * @param filter The filter to add
     */
    public void addFilter(final Predicate<Item> filter) {
        this.filters.add(filter);
        this.applyFilters();
    }

    /**
     * Updates the list of unfiltered items only if they have not been previously set. If updated,
     * this will cause the filtered items to be recalculated using the current filters. Otherwise,
     * nothing is changed.
     *
     * @param items The {@link List} of unfiltered items to use
     */
    public void setOriginalItemsIfEmpty(final List<Item> items) {
        if (this.originalItems == Collections.EMPTY_LIST) {
            this.setOriginalItems(items);
        }
    }

    /**
     * Retrieve an observable list of the items after all the current filters have been applied.
     *
     * @return The {@link LiveData} wrapped list of filtered items
     */
    public LiveData<List<Item>> getFilteredItems() {
        return this.filteredItems;
    }

    /**
     * Updates the filtered items based on the current filters set.
     */
    private void applyFilters() {
        final List<Item> newFilteredItems = this.originalItems.stream()
                .filter(this::matchesFilters)
                .collect(Collectors.toList());

        this.filteredItems.setValue(newFilteredItems);
    }

    /**
     * Determines if the specified {@link Item} matches all the current filters set.
     *
     * @param item The item to check the filters against
     * @return Whether the item matches all the current filters
     */
    private boolean matchesFilters(final Item item) {
        return this.filters.stream().allMatch(filter -> filter.test(item));
    }

    /**
     * Update the list of unfiltered items. This will cause the filtered items to be recalculated
     * using the current filters.
     *
     * @param items The {@link List} of unfiltered items to use
     */
    public void setOriginalItems(final List<Item> items) {
        this.originalItems = items;
        this.applyFilters();
    }
}