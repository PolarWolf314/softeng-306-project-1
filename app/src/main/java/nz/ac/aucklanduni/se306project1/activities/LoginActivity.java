package nz.ac.aucklanduni.se306project1.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.button.MaterialButton;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import nz.ac.aucklanduni.se306project1.R;
import nz.ac.aucklanduni.se306project1.databinding.ActivityLoginBinding;
import nz.ac.aucklanduni.se306project1.dataproviders.UserDataProvider;
import nz.ac.aucklanduni.se306project1.viewmodels.LoginViewModel;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = ActivityLoginBinding.inflate(this.getLayoutInflater());
        setContentView(R.layout.activity_login);

        this.loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        final MaterialButton loginButton = this.findViewById(R.id.login_button);
        loginButton.setOnClickListener(v -> {
            this.authenticateUser().thenAccept(userDataProvider -> {

            }).exceptionally(exception -> {
                // write code to handle login failure
            });
        });
    }

    public CompletableFuture<UserDataProvider> authenticateUser() {
        String email = this.loginViewModel.getEmail();
        String password = this.loginViewModel.getPassword();
        return this.loginViewModel.getAuthenticationProvider().loginUser(email, password).thenApply(
                userDataProvider -> userDataProvider).exceptionally(exception -> null);
    }
}