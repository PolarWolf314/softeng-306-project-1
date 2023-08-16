package nz.ac.aucklanduni.se306project1.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class SearchViewModel<Item> extends ViewModel {
    private final MutableLiveData<List<Predicate<Item>>> filters = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<List<Item>> filteredItems = new MutableLiveData<>();
    private List<Item> originalItems = null;

    public SearchViewModel() {
        // TODO: Check that I don't have to pass these filters into the function
        this.filters.observeForever((filters) -> this.applyFilters());
    }

    /**
     * Removes this filter from the list of filters being applied to the items. This will cause the
     * filtered items to be recalculated.
     *
     * @param filter The filter to remove
     */
    public void removeFilter(final Predicate<Item> filter) {
        final List<Predicate<Item>> currentFilters = this.getFilters();
        currentFilters.remove(filter);
        this.filters.setValue(currentFilters);
    }

    /**
     * Adds this filter to the list of filters being applied to the items. This will cause the
     * filtered items to be recalculated.
     *
     * @param filter The filter to add
     */
    public void addFilter(final Predicate<Item> filter) {
        final List<Predicate<Item>> currentFilters = this.getFilters();
        currentFilters.add(filter);
        this.filters.setValue(currentFilters);
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

    /**
     * Updates the list of unfiltered items only if they have not been previously set. If updated,
     * this will cause the filtered items to be recalculated using the current filters. Otherwise,
     * nothing is changed.
     *
     * @param items The {@link List} of unfiltered items to use
     */
    public void setOriginalItemsIfEmpty(final List<Item> items) {
        if (this.originalItems == null) {
            this.setOriginalItems(items);
        }
    }

    /**
     * Retrieve an observable list of the current filtered items.
     *
     * @return The {@link LiveData} wrapped list of items.
     */
    public LiveData<List<Item>> getFilteredItems() {
        return this.filteredItems;
    }

    /**
     * Updates the filtered items based on the current filters set.
     */
    private void applyFilters() {
        final List<Item> filteredItems = this.originalItems.stream()
                .filter(this::matchesFilters)
                .collect(Collectors.toList());

        this.filteredItems.setValue(filteredItems);
    }

    /**
     * Determines if the specified {@link Item} matches all the current filters set.
     *
     * @param item The item to check the filters against
     * @return Whether the item matches all the current filters
     */
    private boolean matchesFilters(final Item item) {
        return this.getFilters().stream().allMatch(filter -> filter.test(item));
    }

    /**
     * Retrieves the current list of filters. If there is none, a new empty {@link ArrayList} is
     * returned.
     *
     * @return The {@link List} of current filters
     */
    private List<Predicate<Item>> getFilters() {
        final List<Predicate<Item>> filters = this.filters.getValue();
        // This is required as the returned value is marked as nullable
        if (filters == null) return new ArrayList<>();
        return filters;
    }
}
