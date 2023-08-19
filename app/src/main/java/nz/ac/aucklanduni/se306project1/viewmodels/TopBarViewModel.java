package nz.ac.aucklanduni.se306project1.viewmodels;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import nz.ac.aucklanduni.se306project1.iconbuttons.IconButton;
import nz.ac.aucklanduni.se306project1.iconbuttons.SearchIcon;

public class TopBarViewModel extends ViewModel {
    private final MutableLiveData<Boolean> isSearchBarExpanded = new MutableLiveData<>();
    @Nullable
    private IconButton startIconButton = null;

    @Nullable
    private IconButton endIconButton = SearchIcon.INSTANCE;

    public void setSearchBarExpanded(final boolean isExpanded) {
        this.isSearchBarExpanded.setValue(isExpanded);
    }

    public LiveData<Boolean> getIsSearchBarExpanded() {
        return this.isSearchBarExpanded;
    }

    @Nullable
    public IconButton getStartIconButton() {
        return this.startIconButton;
    }

    public void setStartIconButton(@Nullable final IconButton iconButton) {
        this.startIconButton = iconButton;
    }

    @Nullable
    public IconButton getEndIconButton() {
        return this.endIconButton;
    }

    public void setEndIconButton(@Nullable final IconButton iconButton) {
        this.endIconButton = iconButton;
    }
}
