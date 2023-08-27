package nz.ac.aucklanduni.se306project1.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import nz.ac.aucklanduni.se306project1.EngiWearApplication;
import nz.ac.aucklanduni.se306project1.R;
import nz.ac.aucklanduni.se306project1.activities.HomeActivity;
import nz.ac.aucklanduni.se306project1.activities.LoginActivity;
import nz.ac.aucklanduni.se306project1.activities.ProfileActivity;
import nz.ac.aucklanduni.se306project1.activities.ShoppingCartActivity;
import nz.ac.aucklanduni.se306project1.activities.WatchlistActivity;
import nz.ac.aucklanduni.se306project1.data.Constants;
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

        EngiWearApplication engiWear = (EngiWearApplication) this.getActivity().getApplication();
        boolean isUserLoggedIn = engiWear.getUserDataProvider() != null;
        final ViewModelProvider provider = new ViewModelProvider(this.requireActivity());
        this.viewModel = provider.get(BottomNavigationViewModel.class);
        this.binding.bottomNavigation.setSelectedItemId(this.viewModel.getSelectedItemId());
        this.binding.bottomNavigation.setOnItemSelectedListener(item -> {
            final int id = item.getItemId();
            if (id == R.id.navigation_home) {
                final Intent homeIntent = new Intent(BottomNavigationFragment.this.getActivity(),  HomeActivity.class);
                BottomNavigationFragment.this.startActivity(homeIntent);
                this.requireActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;
            } else if (id == R.id.navigation_watchlist) {
                final Intent watchlistIntent = new Intent(BottomNavigationFragment.this.getActivity(), isUserLoggedIn ? WatchlistActivity.class : LoginActivity.class);
                BottomNavigationFragment.this.startActivity(watchlistIntent);
                this.requireActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                if (!isUserLoggedIn) Toast.makeText(this.getActivity(), Constants.ToastMessages.WATCHLIST_REDIRECT, Toast.LENGTH_LONG).show();
                return true;
            } else if (id == R.id.navigation_cart) {
                final Intent cartIntent = new Intent(BottomNavigationFragment.this.getActivity(), isUserLoggedIn ? ShoppingCartActivity.class : LoginActivity.class);
                BottomNavigationFragment.this.startActivity(cartIntent);
                this.requireActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                if (!isUserLoggedIn) Toast.makeText(this.getActivity(), Constants.ToastMessages.SHOPPING_CART_REDIRECT, Toast.LENGTH_LONG).show();
                return true;
            } else if (id == R.id.navigation_profile) {
                final Intent profileIntent = new Intent(BottomNavigationFragment.this.getActivity(), isUserLoggedIn ? ProfileActivity.class : LoginActivity.class);
                BottomNavigationFragment.this.startActivity(profileIntent);
                this.requireActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                if (!isUserLoggedIn) Toast.makeText(this.getActivity(), Constants.ToastMessages.PROFILE_REDIRECT, Toast.LENGTH_LONG).show();
                return true;
            }
            return false;
        });
    }
}