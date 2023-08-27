package nz.ac.aucklanduni.se306project1.viewmodels;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import java.util.HashSet;
import java.util.Set;

import nz.ac.aucklanduni.se306project1.EngiWearApplication;
import nz.ac.aucklanduni.se306project1.dataproviders.UserDataProvider;
import nz.ac.aucklanduni.se306project1.models.items.Item;
import nz.ac.aucklanduni.se306project1.ui.LoadingSpinner;

public class WatchlistViewModel extends WatchlistItemViewModel {
    public static final ViewModelInitializer<WatchlistViewModel> initializer = new ViewModelInitializer<>(
            WatchlistViewModel.class,
            creationExtras -> {
                final EngiWearApplication app = (EngiWearApplication) creationExtras.get(ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY);
                assert app != null;
                return new WatchlistViewModel(app.getUserDataProvider());
            });

    @Nullable
    private LoadingSpinner spinner;

    public WatchlistViewModel(final UserDataProvider userDataProvider) {
        super(userDataProvider);
    }

    public void setSpinner(@Nullable final LoadingSpinner spinner) {
        this.spinner = spinner;
        if (spinner != null) spinner.show();
    }

    @Override
    protected void getWatchlist() {
        this.userDataProvider.getWatchlist().thenAccept(watchlistItems -> {
            this.watchlistItems.setValue(watchlistItems);
            if (this.spinner != null) {
                this.spinner.hide();
            }
        });
    }

    public LiveData<Set<Item>> getWatchlistItems() {
        return this.watchlistItems;
    }

    public void clearWatchlist() {
        this.userDataProvider.clearWatchlist();
        this.watchlistItems.setValue(new HashSet<>());
    }

    public void loadWatchlist() {
        super.getWatchlist();
    }

    @Override
    protected void getWatchlist() {
        // We'll do it using the loadWatchlist method instead of in the constructor.
    }
}
