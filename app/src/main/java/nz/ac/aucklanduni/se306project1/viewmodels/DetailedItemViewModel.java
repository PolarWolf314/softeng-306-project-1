package nz.ac.aucklanduni.se306project1.viewmodels;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import nz.ac.aucklanduni.se306project1.EngiWearApplication;
import nz.ac.aucklanduni.se306project1.dataproviders.ItemDataProvider;

public class DetailedItemViewModel extends ViewModel {

    public static final ViewModelInitializer<DetailedItemViewModel> INITIALIZER = new ViewModelInitializer<>(
            DetailedItemViewModel.class,
            creationExtras -> {
                final EngiWearApplication app = (EngiWearApplication) creationExtras.get(ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY);
                assert app != null;
                return new DetailedItemViewModel(app.getItemDataProvider());
            });
    private final ItemDataProvider itemDataProvider;

    public DetailedItemViewModel(final ItemDataProvider itemDataProvider) {
        this.itemDataProvider = itemDataProvider;
    }

    public ItemDataProvider getItemDataProvider() {
        return this.itemDataProvider;
    }
}
