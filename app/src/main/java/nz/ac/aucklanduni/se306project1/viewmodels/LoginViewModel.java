package nz.ac.aucklanduni.se306project1.viewmodels;

import androidx.lifecycle.ViewModel;

import java.util.concurrent.CompletableFuture;

import nz.ac.aucklanduni.se306project1.dataproviders.AuthenticationProvider;
import nz.ac.aucklanduni.se306project1.dataproviders.FirebaseAuthenticationProvider;
import nz.ac.aucklanduni.se306project1.dataproviders.UserDataProvider;

public class LoginViewModel extends ViewModel {
    private String email;

    private String password;

    private final AuthenticationProvider authProvider = new FirebaseAuthenticationProvider();

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setEmail(String newEmail) {
        this.email = newEmail;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public AuthenticationProvider getAuthenticationProvider() {
        return this.authProvider;
    }

}
