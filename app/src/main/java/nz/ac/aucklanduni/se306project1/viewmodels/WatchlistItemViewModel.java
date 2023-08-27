package nz.ac.aucklanduni.se306project1.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import nz.ac.aucklanduni.se306project1.dataproviders.UserDataProvider;
import nz.ac.aucklanduni.se306project1.models.items.Item;

public abstract class WatchlistItemViewModel extends ViewModel {

    protected final MutableLiveData<Set<Item>> watchlistItems = new MutableLiveData<>(Collections.emptySet());
    protected final UserDataProvider userDataProvider;

    public WatchlistItemViewModel(final UserDataProvider userDataProvider) {
        this.userDataProvider = userDataProvider;
        this.getWatchlist();
    }

    public void addItemToWatchlist(final Item item) {
        this.userDataProvider.addToWatchlist(item.getId());
        final Set<Item> items = this.getItems();
        items.add(item);
        this.watchlistItems.setValue(items);
    }

    public void removeItemFromWatchlist(final Item item) {
        this.userDataProvider.removeFromWatchlist(item.getId());
        final Set<Item> items = this.getItems();
        items.remove(item);
        this.watchlistItems.setValue(items);
    }

    public boolean isInWatchlist(final Item item) {
        return this.getItems().contains(item);
    }

    public boolean isUserLoggedIn() {
        return this.userDataProvider != null;
    }

    private Set<Item> getItems() {
        final Set<Item> items = this.watchlistItems.getValue();
        if (items == null) return new HashSet<>();
        return items;
    }

    protected void getWatchlist() {
        if (this.userDataProvider != null) {
            this.userDataProvider.getWatchlist().thenAccept(this.watchlistItems::setValue);
        }
    }
}
