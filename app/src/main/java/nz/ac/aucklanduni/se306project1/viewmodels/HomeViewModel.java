package nz.ac.aucklanduni.se306project1.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import nz.ac.aucklanduni.se306project1.EngiWearApplication;
import nz.ac.aucklanduni.se306project1.dataproviders.ItemDataProvider;
import nz.ac.aucklanduni.se306project1.dataproviders.UserDataProvider;
import nz.ac.aucklanduni.se306project1.models.enums.Category;
import nz.ac.aucklanduni.se306project1.models.items.Item;

public class HomeViewModel extends WatchlistItemViewModel {
    public static final ViewModelInitializer<HomeViewModel> INITIALIZER = new ViewModelInitializer<>(
            HomeViewModel.class,
            creationExtras -> {
                final EngiWearApplication app = (EngiWearApplication) creationExtras.get(ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY);
                assert app != null;
                return new HomeViewModel(app.getItemDataProvider(), app.getUserDataProvider());
            });


    private static final int NUM_FEATURED_ITEMS = 5;

    final MutableLiveData<List<Item>> featuredItems = new MutableLiveData<>(Collections.emptyList());
    private final ItemDataProvider itemDataProvider;

    public HomeViewModel(final ItemDataProvider itemDataProvider, final UserDataProvider userDataProvider) {
        super(userDataProvider);
        this.itemDataProvider = itemDataProvider;
    }

    public CompletableFuture<List<Item>> getFeaturedItems() {
        return this.itemDataProvider.getFeaturedItems(NUM_FEATURED_ITEMS);
    }

    public CompletableFuture<Integer> getItemCountPerCategory(final Category category) {
        return this.itemDataProvider.getItemCountPerCategory(category);
    }
}
