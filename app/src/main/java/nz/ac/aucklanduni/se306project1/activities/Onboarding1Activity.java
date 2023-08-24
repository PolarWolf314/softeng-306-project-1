package nz.ac.aucklanduni.se306project1.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;

import nz.ac.aucklanduni.se306project1.EngiWearApplication;
import nz.ac.aucklanduni.se306project1.R;
import nz.ac.aucklanduni.se306project1.data.Constants;

public class Onboarding1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        EngiWearApplication engiWear = (EngiWearApplication) this.getApplication();
        try {
            engiWear.setUserDataProvider(engiWear.getAuthenticationProvider().getCurrentUserDataProvider());
            final Intent intent = new Intent(Onboarding1Activity.this, HomeActivity.class);
            Onboarding1Activity.this.startActivity(intent);
            this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } catch (RuntimeException e) {
            if (this.getSharedPreferences(Constants.PreferencesKeys.USER_SETTINGS_FILE_NAME,
                    Context.MODE_PRIVATE).contains(Constants.PreferencesKeys.FIRST_TIME_PREFERENCE_KEY)) {
                final Intent intent = new Intent(Onboarding1Activity.this, LoginActivity.class);
                Onboarding1Activity.this.startActivity(intent);
                this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_onboarding1);


        final MaterialButton nextButton = this.findViewById(R.id.next_button_onboarding1);
        nextButton.setOnClickListener(v -> {
            final Intent intent = new Intent(Onboarding1Activity.this, Onboarding2Activity.class);
            Onboarding1Activity.this.startActivity(intent);
            this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });

        this.getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.green_onboarding));
    }
}