package nz.ac.aucklanduni.se306project1.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

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
        this.fetchWatchlistItems().thenAccept(this.watchlistItems::setValue);
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

    private CompletableFuture<Set<Item>> fetchWatchlistItems() {
        return this.userDataProvider.getWatchlist().thenCompose(watchlist -> {
            final Set<Item> itemList = new HashSet<>();
            final List<String> itemIdList = watchlist.getItemIds();
            final CompletableFuture<?>[] futures = new CompletableFuture[itemIdList.size()];
            for (int i = 0; i < itemIdList.size(); i++) {
                futures[i] = this.itemDataProvider.getItemById(itemIdList.get(i))
                        .thenAccept(itemList::add);
            }
            return CompletableFuture.allOf(futures).thenApply(nothing -> itemList);
        });
    }
}
