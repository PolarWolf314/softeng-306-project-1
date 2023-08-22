package nz.ac.aucklanduni.se306project1.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import nz.ac.aucklanduni.se306project1.R;
import nz.ac.aucklanduni.se306project1.databinding.ActivitySignupBinding;

public class SignupActivity extends AppCompatActivity {
    private ActivitySignupBinding binding;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = ActivitySignupBinding.inflate(this.getLayoutInflater());
        this.setContentView(this.binding.getRoot());


    }
}