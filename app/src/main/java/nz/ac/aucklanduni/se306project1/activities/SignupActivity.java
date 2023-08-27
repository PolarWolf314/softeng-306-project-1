package nz.ac.aucklanduni.se306project1.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import nz.ac.aucklanduni.se306project1.R;
import nz.ac.aucklanduni.se306project1.data.Constants;
import nz.ac.aucklanduni.se306project1.databinding.ActivitySignupBinding;
import nz.ac.aucklanduni.se306project1.exceptions.EmailAlreadyInUseException;
import nz.ac.aucklanduni.se306project1.exceptions.InvalidEmailException;
import nz.ac.aucklanduni.se306project1.exceptions.WeakPasswordException;
import nz.ac.aucklanduni.se306project1.viewmodels.SignupViewModel;

public class SignupActivity extends AppCompatActivity {
    private ActivitySignupBinding binding;

    private SignupViewModel signupViewModel;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = ActivitySignupBinding.inflate(this.getLayoutInflater());
        this.setContentView(this.binding.getRoot());

        this.signupViewModel = new ViewModelProvider(this, ViewModelProvider.Factory.from(SignupViewModel.initializer)).get(SignupViewModel.class);

        this.binding.signupSignUpButton.setOnClickListener(v -> {
            final String firstName = this.binding.signupFirstnameTextInputLayout.getEditText().getText().toString();
            final String lastName = this.binding.signupLastnameTextInputLayout.getEditText().getText().toString();
            final String email = this.binding.signupEmailTextInputLayout.getEditText().getText().toString();
            final String password = this.binding.signupPasswordTextInputLayout.getEditText().getText().toString();
            final String confirmedPassword = this.binding.signupConfirmPasswordTextInputLayout.getEditText().getText().toString();

            if ((!firstName.isEmpty()) && (!lastName.isEmpty()) && (!email.isEmpty()) && (!password.isEmpty())) {
                if (password.equals(confirmedPassword)) {
                    this.signupViewModel.authenticateUser(email, password).thenAccept(nothing -> {
                        final Intent intent = new Intent(SignupActivity.this, HomeActivity.class);
                        this.startActivity(intent);
                        this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    }).exceptionally(exception -> {
                        String errorMessage = "";
                        Class<?> exceptionClass = exception.getCause().getClass();
                        if (exceptionClass.equals(EmailAlreadyInUseException.class)) {
                            errorMessage = Constants.ToastMessages.EMAIL_ALREADY_IN_USE;
                        } else if (exceptionClass.equals(WeakPasswordException.class)) {
                            errorMessage = Constants.ToastMessages.WEAK_PASSWORD;
                        } else if (exceptionClass.equals(InvalidEmailException.class)) {
                            errorMessage = Constants.ToastMessages.INVALID_EMAIL;
                        }
                        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
                        return null;
                    });
                } else {
                    Toast.makeText(this, Constants.ToastMessages.CONFIRMED_PASSWORD_MISMATCH, Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, Constants.ToastMessages.NOT_ALL_FIELDS_FILLED, Toast.LENGTH_LONG).show();
            }
        });

        this.binding.signupSignInButton.setOnClickListener(v -> {
            final Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            this.startActivity(intent);
            this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });
        this.getWindow().setStatusBarColor(this.getResources().getColor(R.color.blue_onboarding));
    }
}