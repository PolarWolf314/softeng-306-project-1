package nz.ac.aucklanduni.se306project1.viewmodels;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import nz.ac.aucklanduni.se306project1.EngiWearApplication;
import nz.ac.aucklanduni.se306project1.dataproviders.ItemDataProvider;

public class DetailsViewModel extends ViewModel {

    public static final ViewModelInitializer<DetailsViewModel> INITIALIZER = new ViewModelInitializer<>(
            DetailsViewModel.class,
            creationExtras -> {
                final EngiWearApplication app = (EngiWearApplication) creationExtras.get(ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY);
                assert app != null;
                return new DetailsViewModel(app.getItemDataProvider());
            });
    private final ItemDataProvider itemDataProvider;

    public DetailsViewModel(final ItemDataProvider itemDataProvider) {
        this.itemDataProvider = itemDataProvider;
    }

    public ItemDataProvider getItemDataProvider() {
        return this.itemDataProvider;
    }
}
