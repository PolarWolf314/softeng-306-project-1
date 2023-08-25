package nz.ac.aucklanduni.se306project1.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import java.util.Set;

import nz.ac.aucklanduni.se306project1.EngiWearApplication;
import nz.ac.aucklanduni.se306project1.dataproviders.UserDataProvider;
import nz.ac.aucklanduni.se306project1.models.items.Item;

public class WatchlistViewModel extends WatchlistItemViewModel {
    public static final ViewModelInitializer<WatchlistViewModel> initializer = new ViewModelInitializer<>(
            WatchlistViewModel.class,
            creationExtras -> {
                final EngiWearApplication app = (EngiWearApplication) creationExtras.get(ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY);
                assert app != null;
                return new WatchlistViewModel(app.getUserDataProvider());
            });

    public WatchlistViewModel(final UserDataProvider userDataProvider) {
        super(userDataProvider);
    }

    public LiveData<Set<Item>> getWatchlistItems() {
        return this.watchlistItems;
    }
}
