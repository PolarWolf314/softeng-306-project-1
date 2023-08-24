package nz.ac.aucklanduni.se306project1.viewmodels;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import nz.ac.aucklanduni.se306project1.EngiWearApplication;
import nz.ac.aucklanduni.se306project1.models.items.Item;

public class WatchlistViewModel extends ViewModel {
    public static final ViewModelInitializer<WatchlistViewModel> initializer = new ViewModelInitializer<>(
            WatchlistViewModel.class,
            creationExtras -> {
                final EngiWearApplication app = (EngiWearApplication) creationExtras.get(ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY);
                assert app != null;
                return new WatchlistViewModel(app);
            });

    private EngiWearApplication engiWear;

    public WatchlistViewModel(EngiWearApplication engiWear) {
        this.engiWear = engiWear;
    }

    public CompletableFuture<List<Item>> getWatchlistItems() {
        return this.engiWear.getUserDataProvider().getWatchlist().thenCompose(watchlist -> {
            List<Item> itemList = new ArrayList<>();
            List<String> itemIdList = watchlist.getItemIds();
            final CompletableFuture<?>[] futures = new CompletableFuture[itemIdList.size()];
            for (int i = 0; i < itemIdList.size(); i++) {
                futures[i] = this.engiWear.getItemDataProvider().getItemById(itemIdList.get(i)).thenAccept(item -> {
                    itemList.add(item);
                });
            }
            return CompletableFuture.allOf(futures).thenApply(nothing -> itemList);
        });
    }
}
