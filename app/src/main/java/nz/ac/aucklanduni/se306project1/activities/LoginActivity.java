package nz.ac.aucklanduni.se306project1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import nz.ac.aucklanduni.se306project1.R;
import nz.ac.aucklanduni.se306project1.databinding.ActivityLoginBinding;
import nz.ac.aucklanduni.se306project1.viewmodels.LoginViewModel;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = ActivityLoginBinding.inflate(this.getLayoutInflater());
        setContentView(R.layout.activity_login);


    }
}