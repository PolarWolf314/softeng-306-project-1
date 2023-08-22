package nz.ac.aucklanduni.se306project1.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;

import nz.ac.aucklanduni.se306project1.R;

public class Onboarding3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_onboarding3);

        final MaterialButton nextButton = this.findViewById(R.id.next_button_onboarding3);
        nextButton.setOnClickListener(v -> {
            final Intent intent = new Intent(Onboarding3Activity.this, LoginActivity.class);
            Onboarding3Activity.this.startActivity(intent);
        });

        this.getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.yellow_onboarding));
    }
}