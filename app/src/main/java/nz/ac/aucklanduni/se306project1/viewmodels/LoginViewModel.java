package nz.ac.aucklanduni.se306project1.viewmodels;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import java.util.concurrent.CompletableFuture;

import nz.ac.aucklanduni.se306project1.EngiWearApplication;
import nz.ac.aucklanduni.se306project1.dataproviders.AuthenticationProvider;
import nz.ac.aucklanduni.se306project1.dataproviders.FirebaseAuthenticationProvider;
import nz.ac.aucklanduni.se306project1.dataproviders.UserDataProvider;

public class LoginViewModel extends ViewModel {
    public static final ViewModelInitializer<LoginViewModel> initializer = new ViewModelInitializer<>(
            LoginViewModel.class,
            creationExtras -> {
                final EngiWearApplication app = (EngiWearApplication) creationExtras.get(ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY);
                assert app != null;
                return new LoginViewModel(app);
            });

    private EngiWearApplication engiWear;

    public LoginViewModel(EngiWearApplication engiWear) {
        this.engiWear = engiWear;
    }

    public CompletableFuture<?> authenticateUser(String email, String password) {
        return this.engiWear.getAuthenticationProvider().loginUser(email, password).thenAccept(userDataProvider ->
            this.engiWear.setUserDataProvider(userDataProvider)).exceptionally(exception -> null);
    }

}
