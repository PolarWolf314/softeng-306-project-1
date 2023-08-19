package nz.ac.aucklanduni.se306project1.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import nz.ac.aucklanduni.se306project1.databinding.FragmentTopBarBinding;
import nz.ac.aucklanduni.se306project1.viewmodels.TopBarViewModel;

public class TopBarFragment extends Fragment {

    private FragmentTopBarBinding binding;
    private TopBarViewModel topBarViewModel;

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

        this.topBarViewModel = new ViewModelProvider(this.requireActivity()).get(TopBarViewModel.class);
        this.binding.endIconButton.setOnClickListener(v -> this.topBarViewModel.setSearchBarExpanded(true));
        this.topBarViewModel.getIsSearchBarExpanded()
                .observe(this.getViewLifecycleOwner(), this::setIsSearchBarExpanded);

        this.binding.topBarSearchView.getRootView().setOnFocusChangeListener((v, isFocused) -> {
            System.out.println("Is focused root: " + isFocused);
        });

        this.binding.topBarSearchView.setOnFocusChangeListener((v, isFocused) -> {
            System.out.println("Is focused: " + isFocused);
        });
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
        this.binding.startIconButton.setVisibility(View.VISIBLE);
        this.binding.topBarTitle.setVisibility(View.VISIBLE);
        this.binding.endIconButton.setVisibility(View.VISIBLE);
        this.binding.topBarSearchView.setVisibility(View.GONE);
    }
}