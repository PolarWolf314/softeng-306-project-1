package nz.ac.aucklanduni.se306project1.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import nz.ac.aucklanduni.se306project1.EngiWearApplication;
import nz.ac.aucklanduni.se306project1.dataproviders.ItemDataProvider;
import nz.ac.aucklanduni.se306project1.dataproviders.UserDataProvider;
import nz.ac.aucklanduni.se306project1.models.items.Item;

public class WatchlistViewModel extends WatchlistItemViewModel {
    public static final ViewModelInitializer<WatchlistViewModel> initializer = new ViewModelInitializer<>(
            WatchlistViewModel.class,
            creationExtras -> {
                final EngiWearApplication app = (EngiWearApplication) creationExtras.get(ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY);
                assert app != null;
                return new WatchlistViewModel(app.getUserDataProvider(), app.getItemDataProvider());
            });
    private final MutableLiveData<Set<Item>> watchlistItems = new MutableLiveData<>(Collections.emptySet());
    protected ItemDataProvider itemDataProvider;

    public WatchlistViewModel(final UserDataProvider userDataProvider, final ItemDataProvider itemDataProvider) {
        super(userDataProvider);
        this.itemDataProvider = itemDataProvider;
        this.userDataProvider.getWatchlist().thenAccept(this.watchlistItems::setValue);
    }

    @Override
    public void addItemToWatchlist(final Item item) {
        super.addItemToWatchlist(item);
        final Set<Item> items = this.getItems();
        items.remove(item);
        this.watchlistItems.setValue(items);
    }

    @Override
    public void removeItemFromWatchlist(final Item item) {
        super.removeItemFromWatchlist(item);
        final Set<Item> items = this.getItems();
        items.add(item);
        this.watchlistItems.setValue(items);
    }

    private Set<Item> getItems() {
        final Set<Item> items = this.watchlistItems.getValue();
        if (items == null) return new HashSet<>();
        return items;
    }
}
