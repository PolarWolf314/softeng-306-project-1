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
    private final MutableLiveData<List<Predicate<Item>>> filters = new MutableLiveData<>(new ArrayList<>());
    private List<Item> originalItems = Collections.emptyList();
    private final MutableLiveData<List<Item>> filteredItems = new MutableLiveData<>(this.originalItems);

    public SearchViewModel() {
        // TODO: Check that I don't have to pass these filters into the function
        this.filters.observeForever((filters) -> this.applyFilters());
    }

    public void removeFilter(final Predicate<Item> filter) {
        final List<Predicate<Item>> currentFilters = this.getFilters();
        currentFilters.remove(filter);
        this.filters.setValue(currentFilters);
    }

    public void addFilter(final Predicate<Item> filter) {
        final List<Predicate<Item>> currentFilters = this.getFilters();
        currentFilters.add(filter);
        this.filters.setValue(currentFilters);
    }

    public void setOriginalItems(final List<Item> items) {
        this.originalItems = items;
        this.applyFilters();
    }

    public LiveData<List<Item>> getFilteredItems() {
        return this.filteredItems;
    }

    private void applyFilters() {
        final List<Item> filteredItems = this.originalItems.stream()
                .filter(this::matchesFilters)
                .collect(Collectors.toList());

        this.filteredItems.setValue(filteredItems);
    }

    private boolean matchesFilters(final Item item) {
        return this.getFilters().stream().allMatch(filter -> filter.test(item));
    }

    private List<Predicate<Item>> getFilters() {
        final List<Predicate<Item>> filters = this.filters.getValue();
        // This is required as the returned value is marked as nullable
        if (filters == null) return new ArrayList<>();
        return filters;
    }
}
