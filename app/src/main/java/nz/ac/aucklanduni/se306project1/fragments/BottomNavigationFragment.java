package nz.ac.aucklanduni.se306project1.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import nz.ac.aucklanduni.se306project1.R;
import nz.ac.aucklanduni.se306project1.activities.HomeActivity;
import nz.ac.aucklanduni.se306project1.activities.ShoppingCartActivity;
import nz.ac.aucklanduni.se306project1.activities.WatchlistActivity;
import nz.ac.aucklanduni.se306project1.databinding.FragmentBottomNavigationBinding;
import nz.ac.aucklanduni.se306project1.viewmodels.BottomNavigationViewModel;

public class BottomNavigationFragment extends Fragment {
    private BottomNavigationViewModel viewModel;

    private FragmentBottomNavigationBinding binding;

    public BottomNavigationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        this.binding = FragmentBottomNavigationBinding.inflate(inflater, container, false);
        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ViewModelProvider provider = new ViewModelProvider(this.requireActivity());
        this.viewModel = provider.get(BottomNavigationViewModel.class);
        this.binding.bottomNavigation.setSelectedItemId(this.viewModel.getSelectedItemId());
        this.binding.bottomNavigation.setOnItemSelectedListener(item -> {
            final int id = item.getItemId();
            if (id == R.id.navigation_home) {
                final Intent homeIntent = new Intent(BottomNavigationFragment.this.getActivity(), HomeActivity.class);
                BottomNavigationFragment.this.startActivity(homeIntent);
                this.requireActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;
            } else if (id == R.id.navigation_watchlist) {
                final Intent watchlistIntent = new Intent(BottomNavigationFragment.this.getActivity(), WatchlistActivity.class);
                BottomNavigationFragment.this.startActivity(watchlistIntent);
                this.requireActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;
            } else if (id == R.id.navigation_cart) {
                final Intent cartIntent = new Intent(BottomNavigationFragment.this.getActivity(), ShoppingCartActivity.class);
                BottomNavigationFragment.this.startActivity(cartIntent);
                this.requireActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;
            }
            return false;
        });
    }
}