package nz.ac.aucklanduni.se306project1.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import nz.ac.aucklanduni.se306project1.EngiWearApplication;
import nz.ac.aucklanduni.se306project1.dataproviders.ItemDataProvider;
import nz.ac.aucklanduni.se306project1.models.items.ColouredItemInformation;

public class DetailsViewModel extends ViewModel {

    public static final ViewModelInitializer<DetailsViewModel> INITIALIZER = new ViewModelInitializer<>(
            DetailsViewModel.class,
            creationExtras -> {
                final EngiWearApplication app = (EngiWearApplication) creationExtras.get(ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY);
                assert app != null;
                return new DetailsViewModel(app.getItemDataProvider());
            });
    private final ItemDataProvider itemDataProvider;
    private final MutableLiveData<ColouredItemInformation> selectedColourInfo = new MutableLiveData<>();

    public DetailsViewModel(final ItemDataProvider itemDataProvider) {
        this.itemDataProvider = itemDataProvider;
    }

    public LiveData<ColouredItemInformation> getSelectedColourInfo() {
        return this.selectedColourInfo;
    }

    public void setSelectedColourInfo(final ColouredItemInformation selectedColourInfo) {
        this.selectedColourInfo.setValue(selectedColourInfo);
    }

    public ItemDataProvider getItemDataProvider() {
        return this.itemDataProvider;
    }
}
