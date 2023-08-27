package nz.ac.aucklanduni.se306project1.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import nz.ac.aucklanduni.se306project1.EngiWearApplication;
import nz.ac.aucklanduni.se306project1.dataproviders.ItemDataProvider;
import nz.ac.aucklanduni.se306project1.dataproviders.UserDataProvider;
import nz.ac.aucklanduni.se306project1.models.items.ColouredItemInformation;
import nz.ac.aucklanduni.se306project1.models.items.Item;
import nz.ac.aucklanduni.se306project1.models.items.SerializedCartItem;

public class DetailsViewModel extends ViewModel {

    public static final ViewModelInitializer<DetailsViewModel> INITIALIZER = new ViewModelInitializer<>(
            DetailsViewModel.class,
            creationExtras -> {
                final EngiWearApplication app = (EngiWearApplication) creationExtras.get(ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY);
                assert app != null;
                return new DetailsViewModel(app.getItemDataProvider(), app.getUserDataProvider());
            });
    private final ItemDataProvider itemDataProvider;
    private final UserDataProvider userDataProvider;
    private final MutableLiveData<ColouredItemInformation> selectedColourInfo = new MutableLiveData<>();
    private Item item;
    private String selectedSize = null;
    private List<String> currentSizes = Collections.emptyList();
    private final MutableLiveData<Boolean> isInWatchlist = new MutableLiveData<>();

    public DetailsViewModel(final ItemDataProvider itemDataProvider, final UserDataProvider userDataProvider) {
        this.itemDataProvider = itemDataProvider;
        this.userDataProvider = userDataProvider;
    }

    public void setItem(final Item item) {
        this.item = item;
    }

    public CompletableFuture<Item> getItemById(String itemId) {
        return this.itemDataProvider.getItemById(itemId);
    }

    public LiveData<Boolean> isInWatchlist() {
        return this.isInWatchlist;
    }

    public void toggleIsInWatchlist() {
        final Boolean isInWatchlist = this.isInWatchlist.getValue();
        if (isInWatchlist == null) {
            this.isInWatchlist.setValue(true);
        } else {
            this.isInWatchlist.setValue(!isInWatchlist);
        }

        // TODO: Update user data provider
    }

    public String getSelectedSize() {
        return this.selectedSize;
    }

    public void setSelectedSize(final String selectedSize) {
        this.selectedSize = selectedSize;
    }

    public List<String> getCurrentSizes() {
        return this.currentSizes;
    }

    public void setCurrentSizes(final List<String> currentSizes) {
        this.currentSizes = currentSizes;
    }

    public LiveData<ColouredItemInformation> getSelectedColourInfo() {
        return this.selectedColourInfo;
    }

    public void setSelectedColourInfo(final ColouredItemInformation selectedColourInfo) {
        this.selectedColourInfo.setValue(selectedColourInfo);
    }

    public void addToCart() {
        if (item != null) {
            this.userDataProvider.addToShoppingCart(new SerializedCartItem(1, this.selectedColourInfo.getValue().getColour(),
                    this.selectedSize, this.item.getId()));
        }
    }

    public boolean isUserLoggedIn() {
        return this.userDataProvider != null;
    }
}
