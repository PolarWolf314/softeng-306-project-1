package nz.ac.aucklanduni.se306project1.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import nz.ac.aucklanduni.se306project1.R;

public class Onboarding2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_onboarding2);


        final MaterialButton nextButton = this.findViewById(R.id.next_button_onboarding2);
        nextButton.setOnClickListener(v -> {
            final Intent intent = new Intent(Onboarding2Activity.this, Onboarding3Activity.class);
            Onboarding2Activity.this.startActivity(intent);
            this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });
    }
}