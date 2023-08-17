package nz.ac.aucklanduni.se306project1.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import nz.ac.aucklanduni.se306project1.databinding.FragmentTopBarBinding;

public class TopBarFragment extends Fragment {

    private FragmentTopBarBinding binding;

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

        this.binding.endIconButton.setOnClickListener(v -> this.showSearchBar());
    }

    private void showSearchBar() {
        this.binding.startIconButton.setVisibility(View.GONE);
        this.binding.topBarTitle.setVisibility(View.GONE);
        this.binding.endIconButton.setVisibility(View.GONE);
        this.binding.searchBar.setVisibility(View.VISIBLE);
    }

    private void hideSearchBar() {
        this.binding.startIconButton.setVisibility(View.VISIBLE);
        this.binding.topBarTitle.setVisibility(View.VISIBLE);
        this.binding.endIconButton.setVisibility(View.VISIBLE);
        this.binding.searchBar.setVisibility(View.GONE);
    }
}