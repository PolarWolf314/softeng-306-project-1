package nz.ac.aucklanduni.se306project1.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
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
            String email = this.binding.signupEmailTextInputLayout.getEditText().getText().toString();
            String password = this.binding.signupPasswordTextInputLayout.getEditText().getText().toString();
            String confirmedPassword = this.binding.signupConfirmPasswordTextInputLayout.getEditText().getText().toString();
            if (password.equals(confirmedPassword)) {
                this.signupViewModel.authenticateUser(email, password).thenAccept(nothing -> {
                    final Intent intent = new Intent(SignupActivity.this, HomeActivity.class);
                    startActivity(intent);
                });
            }
        });
    }
}