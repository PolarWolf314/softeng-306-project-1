package nz.ac.aucklanduni.se306project1.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

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
        setContentView(this.binding.getRoot());

        this.loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        this.binding.loginButton.setOnClickListener(v -> {
            this.authenticateUser().thenAccept(userDataProvider -> {
                final Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
            });
        });
    }

    public CompletableFuture<UserDataProvider> authenticateUser() {
        Log.i("lol", "hi");
        String email = this.binding.usernameTextInputLayout.getEditText().getText().toString();
        String password = this.binding.passwordTextInputLayout.getEditText().getText().toString();
        Log.i("lol", email);
        Log.i("lol", password);
        return this.loginViewModel.getAuthenticationProvider().loginUser(email, password).thenApply(
                userDataProvider -> userDataProvider).exceptionally(exception -> null);
    }
}