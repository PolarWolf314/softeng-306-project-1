package nz.ac.aucklanduni.se306project1.viewmodels;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import nz.ac.aucklanduni.se306project1.EngiWearApplication;
import nz.ac.aucklanduni.se306project1.dataproviders.ItemDataProvider;
import nz.ac.aucklanduni.se306project1.dataproviders.UserDataProvider;
import nz.ac.aucklanduni.se306project1.models.enums.Category;
import nz.ac.aucklanduni.se306project1.models.items.Item;

public class ListViewModel extends WatchlistItemViewModel {
    public static final ViewModelInitializer<ListViewModel> INITIALIZER = new ViewModelInitializer<>(
            ListViewModel.class,
            creationExtras -> {
                final EngiWearApplication app = (EngiWearApplication) creationExtras.get(ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY);
                assert app != null;
                return new ListViewModel(app.getItemDataProvider(), app.getUserDataProvider());
            });
    private final ItemDataProvider itemDataProvider;

    public ListViewModel(final ItemDataProvider itemDataProvider, final UserDataProvider userDataProvider) {
        super(userDataProvider);
        this.itemDataProvider = itemDataProvider;
    }

    public CompletableFuture<List<Item>> getItemsByCategory(final Category category) {
        return this.itemDataProvider.getItemsByCategory(category);
    }

    public CompletableFuture<List<Item>> getAllItems() {
        return this.itemDataProvider.getAllItems();
    }
}
