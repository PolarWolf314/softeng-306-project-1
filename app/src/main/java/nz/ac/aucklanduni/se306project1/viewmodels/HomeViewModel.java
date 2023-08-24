package nz.ac.aucklanduni.se306project1.viewmodels;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import nz.ac.aucklanduni.se306project1.EngiWearApplication;
import nz.ac.aucklanduni.se306project1.models.items.Item;

public class HomeViewModel extends ViewModel {
    public static final ViewModelInitializer<HomeViewModel> initializer = new ViewModelInitializer<>(
            HomeViewModel.class,
            creationExtras -> {
                final EngiWearApplication app = (EngiWearApplication) creationExtras.get(ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY);
                assert app != null;
                return new HomeViewModel(app);
            });

    private EngiWearApplication engiWear;

    public HomeViewModel(EngiWearApplication engiWear) {
        this.engiWear = engiWear;
    }

    public CompletableFuture<List<Item>> getFeaturedItems(int numItems) {
        return this.engiWear.getItemDataProvider().getFeaturedItems(numItems);
    }
}
