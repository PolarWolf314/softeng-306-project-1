package nz.ac.aucklanduni.se306project1.viewmodels;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import java.util.concurrent.CompletableFuture;

import nz.ac.aucklanduni.se306project1.EngiWearApplication;

public class SignupViewModel extends ViewModel {
    public static final ViewModelInitializer<SignupViewModel> initializer = new ViewModelInitializer<>(
            SignupViewModel.class,
            creationExtras -> {
                final EngiWearApplication app = (EngiWearApplication) creationExtras.get(ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY);
                assert app != null;
                return new SignupViewModel(app);
            });

    private EngiWearApplication engiWear;

    public SignupViewModel(EngiWearApplication engiWear) {
        this.engiWear = engiWear;
    }

    public CompletableFuture<?> authenticateUser(String email, String password) {
        return this.engiWear.getAuthenticationProvider().registerUser(email, password).thenAccept(userDataProvider ->
                this.engiWear.setUserDataProvider(userDataProvider));
    }
}
