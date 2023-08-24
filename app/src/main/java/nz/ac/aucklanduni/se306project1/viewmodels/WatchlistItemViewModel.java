package nz.ac.aucklanduni.se306project1.viewmodels;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import nz.ac.aucklanduni.se306project1.EngiWearApplication;
import nz.ac.aucklanduni.se306project1.dataproviders.UserDataProvider;
import nz.ac.aucklanduni.se306project1.models.items.Item;

public class WatchlistItemViewModel extends ViewModel {

    public static final ViewModelInitializer<WatchlistItemViewModel> INITIALIZER = new ViewModelInitializer<>(
            WatchlistItemViewModel.class,
            creationExtras -> {
                final EngiWearApplication app = (EngiWearApplication) creationExtras.get(ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY);
                assert app != null;
                return new WatchlistItemViewModel(app.getUserDataProvider());
            });

    protected final UserDataProvider userDataProvider;

    public WatchlistItemViewModel(final UserDataProvider userDataProvider) {
        this.userDataProvider = userDataProvider;
    }

    public void addItemToWatchlist(final Item item) {
        this.userDataProvider.addToWatchlist(item.getId());
    }

    public void removeItemFromWatchlist(final Item item) {
        this.userDataProvider.removeFromWatchlist(item.getId());
    }
}
