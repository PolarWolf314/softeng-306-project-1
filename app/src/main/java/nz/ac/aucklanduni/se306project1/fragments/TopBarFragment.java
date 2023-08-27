package nz.ac.aucklanduni.se306project1.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;

import nz.ac.aucklanduni.se306project1.data.Constants;
import nz.ac.aucklanduni.se306project1.databinding.FragmentTopBarBinding;
import nz.ac.aucklanduni.se306project1.iconbuttons.IconButton;
import nz.ac.aucklanduni.se306project1.models.SearchFilterable;
import nz.ac.aucklanduni.se306project1.utils.QueryUtils;
import nz.ac.aucklanduni.se306project1.viewmodels.SearchViewModel;
import nz.ac.aucklanduni.se306project1.viewmodels.TopBarViewModel;

public class TopBarFragment extends Fragment {

    private FragmentTopBarBinding binding;
    private TopBarViewModel viewModel;
    private SearchViewModel<? extends SearchFilterable> searchViewModel;

    @Override
    public View onCreateView(
            @NonNull final LayoutInflater inflater,
            final ViewGroup container,
            final Bundle savedInstanceState
    ) {
        this.binding = FragmentTopBarBinding.inflate(inflater, container, false);
        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ViewModelProvider provider = new ViewModelProvider(this.requireActivity());
        this.viewModel = provider.get(TopBarViewModel.class);
        this.searchViewModel = provider.get(this.viewModel.getSearchViewModelClass());

        this.bindTopBarData();

        this.binding.topBarSearchView.setOnQueryTextListener(
                QueryUtils.createQueryChangeListener(this::onQueryChange));
    }

    private void bindTopBarData() {

        final LifecycleOwner lifecycleOwner = this.getViewLifecycleOwner();

        this.bindIconButton(this.binding.startIconButton, this.viewModel.getStartIconButton());

        this.viewModel.getTitle()
                .observe(lifecycleOwner, (newTitle) -> this.binding.topBarTitle.setText(newTitle));
        this.viewModel.getTitleColour()
                .observe(lifecycleOwner, (newColour) -> this.binding.topBarTitle.setTextColor(newColour));
        this.viewModel.getEndIconButton()
                .observe(lifecycleOwner, iconButton -> this.bindIconButton(this.binding.endIconButton, iconButton));
        this.viewModel.getIsSearchBarExpanded()
                .observe(this.getViewLifecycleOwner(), this::setIsSearchBarExpanded);
    }

    private void onQueryChange(final String newQuery) {
        this.searchViewModel.removeFilter(Constants.FilterKeys.SEARCH_QUERY);
        final String loweredQuery = newQuery.trim().toLowerCase();

        if (loweredQuery.length() != 0) {
            this.searchViewModel.putFilter(Constants.FilterKeys.SEARCH_QUERY, (item) -> item.matches(loweredQuery));
        }
    }

    private void bindIconButton(final MaterialButton button, @Nullable final IconButton iconButton) {
        if (iconButton == null) {
            button.setVisibility(View.GONE);
        } else {
            button.setVisibility(View.VISIBLE);
            button.setIconResource(iconButton.getIconId());
            button.setOnClickListener((v) ->
                    iconButton.getOnClickListener().accept(this.getActivity(), this.viewModel));
        }
    }

    private void setIsSearchBarExpanded(final boolean isExpanded) {
        if (isExpanded) this.showSearchBar();
        else this.hideSearchBar();
    }

    private void showSearchBar() {
        this.binding.startIconButton.setVisibility(View.GONE);
        this.binding.topBarTitle.setVisibility(View.GONE);
        this.binding.endIconButton.setVisibility(View.GONE);
        this.binding.topBarSearchView.onActionViewExpanded();
        this.binding.topBarSearchView.setIconified(false);
        this.binding.topBarSearchView.setVisibility(View.VISIBLE);
    }

    private void hideSearchBar() {
        this.binding.startIconButton.setVisibility(this.getVisibility(this.viewModel.getStartIconButton()));
        this.binding.topBarTitle.setVisibility(View.VISIBLE);
        this.binding.endIconButton.setVisibility(View.VISIBLE);
        this.binding.topBarSearchView.setVisibility(View.GONE);
    }

    private int getVisibility(@Nullable final IconButton iconButton) {
        return iconButton == null ? View.GONE : View.VISIBLE;
    }
}