package nz.ac.aucklanduni.se306project1.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TopBarViewModel extends ViewModel {
    private final MutableLiveData<Boolean> isSearchBarExpanded = new MutableLiveData<>(false);

    public void setSearchBarExpanded(final boolean isExpanded) {
        this.isSearchBarExpanded.setValue(isExpanded);
    }

    public LiveData<Boolean> getIsSearchBarExpanded() {
        return this.isSearchBarExpanded;
    }
}
