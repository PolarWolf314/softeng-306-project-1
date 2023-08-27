package nz.ac.aucklanduni.se306project1.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import nz.ac.aucklanduni.se306project1.R;
import nz.ac.aucklanduni.se306project1.data.Constants;
import nz.ac.aucklanduni.se306project1.databinding.ActivitySignupBinding;
import nz.ac.aucklanduni.se306project1.exceptions.EmailAlreadyInUseException;
import nz.ac.aucklanduni.se306project1.exceptions.InvalidEmailException;
import nz.ac.aucklanduni.se306project1.exceptions.WeakPasswordException;
import nz.ac.aucklanduni.se306project1.ui.LoadingSpinner;
import nz.ac.aucklanduni.se306project1.viewmodels.SignupViewModel;

public class SignupActivity extends AppCompatActivity {

    private ActivitySignupBinding binding;
    private SignupViewModel signupViewModel;
    private LoadingSpinner spinner;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = ActivitySignupBinding.inflate(this.getLayoutInflater());
        this.setContentView(this.binding.getRoot());

        this.signupViewModel = new ViewModelProvider(this, ViewModelProvider.Factory.from(SignupViewModel.initializer)).get(SignupViewModel.class);

        this.binding.signupSignUpButton.setOnClickListener(v -> this.onSignup());
        this.binding.signupSignInButton.setOnClickListener(v -> this.onGoToLogin());

        this.spinner = new LoadingSpinner(this.binding.getRoot());
        this.spinner.setColor(this.getResources().getColor(R.color.blue_onboarding, this.getTheme()));

        this.getWindow().setStatusBarColor(this.getResources().getColor(R.color.blue_onboarding, this.getTheme()));
    }

    private void onSignup() {
        final String firstName = this.binding.signupFirstnameTextInputLayout.getEditText().getText().toString();
        final String lastName = this.binding.signupLastnameTextInputLayout.getEditText().getText().toString();
        final String email = this.binding.signupEmailTextInputLayout.getEditText().getText().toString();
        final String password = this.binding.signupPasswordTextInputLayout.getEditText().getText().toString();
        final String confirmedPassword = this.binding.signupConfirmPasswordTextInputLayout.getEditText().getText().toString();

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, Constants.ToastMessages.NOT_ALL_FIELDS_FILLED, Toast.LENGTH_LONG).show();
            return;
        }

        if (!password.equals(confirmedPassword)) {
            Toast.makeText(this, Constants.ToastMessages.CONFIRMED_PASSWORD_MISMATCH, Toast.LENGTH_LONG).show();
            return;
        }

        this.spinner.show();
        this.signupViewModel.authenticateUser(email, password)
                .whenComplete((nothing, exception) -> {
                    if (exception != null) {
                        final String errorMessage = this.determineErrorMessage(exception.getCause());
                        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
                    } else {
                        this.switchToActivity(HomeActivity.class);
                    }
                    this.spinner.hide();
                });
    }

    private String determineErrorMessage(@Nullable final Throwable exception) {
        if (exception instanceof EmailAlreadyInUseException) {
            return Constants.ToastMessages.EMAIL_ALREADY_IN_USE;
        } else if (exception instanceof WeakPasswordException) {
            return Constants.ToastMessages.WEAK_PASSWORD;
        } else if (exception instanceof InvalidEmailException) {
            return Constants.ToastMessages.INVALID_EMAIL;
        }
        return Constants.ToastMessages.GENERIC_FAILURE;
    }

    private void onGoToLogin() {
        this.switchToActivity(LoginActivity.class);
    }

    private void switchToActivity(final Class<? extends Activity> activity) {
        final Intent intent = new Intent(this, activity);
        this.startActivity(intent);
        this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

}