package nz.ac.aucklanduni.se306project1.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Collections;
import java.util.List;

import nz.ac.aucklanduni.se306project1.iconbuttons.IconButton;

public class TopBarViewModel extends ViewModel {
    private final MutableLiveData<Boolean> isSearchBarExpanded = new MutableLiveData<>();
    private IconButton startIconButton = null;
    private List<IconButton> endIconButtons = Collections.emptyList();

    public void setSearchBarExpanded(final boolean isExpanded) {
        this.isSearchBarExpanded.setValue(isExpanded);
    }

    public LiveData<Boolean> getIsSearchBarExpanded() {
        return this.isSearchBarExpanded;
    }

    public IconButton getStartIconButton() {
        return this.startIconButton;
    }

    public void setStartIconButton(final IconButton iconButton) {
        this.startIconButton = iconButton;
    }

    public List<IconButton> getEndIconButtons() {
        return this.endIconButtons;
    }

    public void setEndIconButtons(final IconButton... iconButtons) {
        this.endIconButtons = List.of(iconButtons);
    }
}
