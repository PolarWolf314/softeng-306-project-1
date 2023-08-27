package nz.ac.aucklanduni.se306project1.viewmodels;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import nz.ac.aucklanduni.se306project1.EngiWearApplication;
import nz.ac.aucklanduni.se306project1.dataproviders.AuthenticationProvider;

public class ProfileViewModel extends ViewModel {
    public static final ViewModelInitializer<ProfileViewModel> initializer = new ViewModelInitializer<>(
            ProfileViewModel.class,
            creationExtras -> {
                final EngiWearApplication app = (EngiWearApplication) creationExtras.get(ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY);
                assert app != null;
                return new ProfileViewModel(app);
            });

    private EngiWearApplication engiWear;

    public ProfileViewModel(EngiWearApplication engiWear) {
        this.engiWear = engiWear;
    }

    public void signoutUser() {
        this.engiWear.getAuthenticationProvider().logoutUser();
    }

    public void clearUserData() {
        this.engiWear.getUserDataProvider().clearShoppingCart();
        this.engiWear.getUserDataProvider().clearWatchlist();
    }

    public String getUserEmailAddress() {
        return this.engiWear.getUserDataProvider().getUserEmailAddress();
    }

    public boolean isUserLoggedIn() {
        return this.engiWear.getUserDataProvider() != null;
    }
}
