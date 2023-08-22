package nz.ac.aucklanduni.se306project1.viewmodels;

import androidx.lifecycle.ViewModel;

import java.util.concurrent.CompletableFuture;

import nz.ac.aucklanduni.se306project1.dataproviders.AuthenticationProvider;
import nz.ac.aucklanduni.se306project1.dataproviders.FirebaseAuthenticationProvider;
import nz.ac.aucklanduni.se306project1.dataproviders.UserDataProvider;

public class LoginViewModel extends ViewModel {

    private final AuthenticationProvider authProvider = new FirebaseAuthenticationProvider();

    public AuthenticationProvider getAuthenticationProvider() {
        return this.authProvider;
    }

}
