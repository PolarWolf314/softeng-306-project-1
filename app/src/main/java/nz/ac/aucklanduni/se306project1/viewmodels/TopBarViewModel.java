package nz.ac.aucklanduni.se306project1.viewmodels;

import android.graphics.Color;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import nz.ac.aucklanduni.se306project1.iconbuttons.IconButton;
import nz.ac.aucklanduni.se306project1.iconbuttons.SearchIcon;
import nz.ac.aucklanduni.se306project1.models.SearchFilterable;

public class TopBarViewModel extends ViewModel {
    private final MutableLiveData<Boolean> isSearchBarExpanded = new MutableLiveData<>();
    private final MutableLiveData<String> title = new MutableLiveData<>();
    private final MutableLiveData<IconButton> endIconButton = new MutableLiveData<>(SearchIcon.INSTANCE);
    private final MutableLiveData<Integer> titleColour = new MutableLiveData<>(Color.WHITE);
    private Class<? extends SearchViewModel<? extends SearchFilterable>> searchViewModelClass = ItemSearchViewModel.class;
    @Nullable
    private IconButton startIconButton = null;

    public LiveData<Integer> getTitleColour() {
        return this.titleColour;
    }

    public void setTitleColour(final @ColorInt int titleColour) {
        this.titleColour.setValue(titleColour);
    }

    public LiveData<String> getTitle() {
        return this.title;
    }

    public void setTitle(@Nullable final String title) {
        this.title.setValue(title);
    }

    public Class<? extends SearchViewModel<? extends SearchFilterable>> getSearchViewModelClass() {
        return this.searchViewModelClass;
    }

    public void setSearchViewModelClass(final Class<? extends SearchViewModel<? extends SearchFilterable>> searchViewModelClass) {
        this.searchViewModelClass = searchViewModelClass;
    }

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

    public LiveData<IconButton> getEndIconButton() {
        return this.endIconButton;
    }

    public void setEndIconButton(@Nullable final IconButton iconButton) {
        this.endIconButton.setValue(iconButton);
    }
}
