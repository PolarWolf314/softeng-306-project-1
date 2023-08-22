package nz.ac.aucklanduni.se306project1.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import nz.ac.aucklanduni.se306project1.R;
import nz.ac.aucklanduni.se306project1.databinding.ActivityLoginBinding;
import nz.ac.aucklanduni.se306project1.viewmodels.LoginViewModel;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = ActivityLoginBinding.inflate(this.getLayoutInflater());
        this.setContentView(this.binding.getRoot());

        this.loginViewModel = new ViewModelProvider(this, ViewModelProvider.Factory.from(LoginViewModel.initializer)).get(LoginViewModel.class);

        this.binding.loginButton.setOnClickListener(v -> {
            final String email = this.binding.usernameTextInputLayout.getEditText().getText().toString();
            final String password = this.binding.passwordTextInputLayout.getEditText().getText().toString();
            this.loginViewModel.authenticateUser(email, password).thenAccept(nothing -> {
                final Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                this.startActivity(intent);
            });
        });

        this.binding.signUpButton.setOnClickListener(v -> {
            final Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            this.startActivity(intent);
        });
        this.getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.blue_onboarding));
    }
}