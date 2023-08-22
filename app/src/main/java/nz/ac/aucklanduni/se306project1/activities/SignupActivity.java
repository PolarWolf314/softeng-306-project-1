package nz.ac.aucklanduni.se306project1.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import nz.ac.aucklanduni.se306project1.R;
import nz.ac.aucklanduni.se306project1.databinding.ActivitySignupBinding;
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
            final String email = this.binding.signupEmailTextInputLayout.getEditText().getText().toString();
            final String password = this.binding.signupPasswordTextInputLayout.getEditText().getText().toString();
            final String confirmedPassword = this.binding.signupConfirmPasswordTextInputLayout.getEditText().getText().toString();
            if (password.equals(confirmedPassword)) {
                this.signupViewModel.authenticateUser(email, password).thenAccept(nothing -> {
                    final Intent intent = new Intent(SignupActivity.this, HomeActivity.class);
                    this.startActivity(intent);
                });
            }
        });

        this.binding.signupSignInButton.setOnClickListener(v -> {
            final Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            this.startActivity(intent);
        });
        this.getWindow().setStatusBarColor(this.getResources().getColor(R.color.blue_onboarding));
    }
}