package nz.ac.aucklanduni.se306project1.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import nz.ac.aucklanduni.se306project1.R;
import nz.ac.aucklanduni.se306project1.data.Constants;
import nz.ac.aucklanduni.se306project1.databinding.ActivityLoginBinding;
import nz.ac.aucklanduni.se306project1.ui.LoadingSpinner;
import nz.ac.aucklanduni.se306project1.viewmodels.LoginViewModel;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    private LoginViewModel loginViewModel;
    private LoadingSpinner spinner;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = ActivityLoginBinding.inflate(this.getLayoutInflater());
        this.setContentView(this.binding.getRoot());

        this.getSharedPreferences("User settings", Context.MODE_PRIVATE).edit().putBoolean("firstTime", false).apply();

        this.loginViewModel = new ViewModelProvider(this, ViewModelProvider.Factory.from(LoginViewModel.initializer)).get(LoginViewModel.class);

        this.binding.loginButton.setOnClickListener(v -> this.onLogin());
        this.binding.signUpButton.setOnClickListener(v -> this.onGoToSignup());
        this.binding.continueAsGuest.setOnClickListener(v -> this.onContinueAsGuest());

        this.spinner = new LoadingSpinner(this.binding.getRoot());
        this.spinner.setColor(this.getResources().getColor(R.color.primary_alt, this.getTheme()));

        this.getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.blue_onboarding));
    }

    private void onLogin() {
        final String email = this.binding.usernameTextInputLayout.getEditText().getText().toString();
        final String password = this.binding.passwordTextInputLayout.getEditText().getText().toString();

        if (!email.isEmpty() && !password.isEmpty()) {
            this.spinner.show();
            this.loginViewModel.authenticateUser(email, password)
                    .whenComplete((nothing, exception) -> {
                        if (exception != null) {
                            Toast.makeText(this, Constants.ToastMessages.INCORRECT_USERNAME_OR_PASSWORD, Toast.LENGTH_LONG).show();
                        } else {
                            this.switchToActivity(HomeActivity.class);
                        }
                        this.spinner.hide();
                    });
        } else {
            Toast.makeText(this, Constants.ToastMessages.NOT_ALL_FIELDS_FILLED, Toast.LENGTH_LONG).show();
        }
    }

    private void onGoToSignup() {
        this.switchToActivity(SignupActivity.class);
    }

    private void onContinueAsGuest() {
        this.switchToActivity(HomeActivity.class);
    }

    private void switchToActivity(final Class<? extends Activity> activity) {
        final Intent intent = new Intent(this, activity);
        this.startActivity(intent);
        this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }


}