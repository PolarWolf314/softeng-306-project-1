package nz.ac.aucklanduni.se306project1.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import nz.ac.aucklanduni.se306project1.R;
import nz.ac.aucklanduni.se306project1.data.Constants;
import nz.ac.aucklanduni.se306project1.databinding.ActivityProfileBinding;
import nz.ac.aucklanduni.se306project1.viewmodels.BottomNavigationViewModel;
import nz.ac.aucklanduni.se306project1.viewmodels.LoginViewModel;
import nz.ac.aucklanduni.se306project1.viewmodels.ProfileViewModel;

public class ProfileActivity extends AppCompatActivity {

    private BottomNavigationViewModel bottomNavigationViewModel;
    private ActivityProfileBinding binding;

    private ProfileViewModel profileViewModel;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityProfileBinding.inflate(this.getLayoutInflater());
        this.setContentView(this.binding.getRoot());

        this.profileViewModel = new ViewModelProvider(this, ViewModelProvider.Factory.from(ProfileViewModel.initializer)).get(ProfileViewModel.class);
        this.binding.userNameTextView.setText(this.profileViewModel.getUserEmailAddress());

        this.binding.logoutButton.setOnClickListener(v -> {
            this.profileViewModel.signoutUser();
            final Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            ProfileActivity.this.startActivity(intent);
            this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });

        this.binding.clearDataButton.setOnClickListener(v -> {
            this.profileViewModel.clearUserData();
            Toast.makeText(this, Constants.ToastMessages.USER_DATA_CLEARED, Toast.LENGTH_LONG).show();
        });

        this.bottomNavigationViewModel = new ViewModelProvider(this).get(BottomNavigationViewModel.class);
        this.bottomNavigationViewModel.setSelectedItemId(R.id.navigation_profile);
        this.getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.red_top_bar));
        this.getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.background_light_gray));
    }
}