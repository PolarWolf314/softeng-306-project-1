package nz.ac.aucklanduni.se306project1.dataproviders;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseUser;

public interface AuthenticationProvider {

    FirebaseUser registerUser(String email, String password, AppCompatActivity myActivity);

    FirebaseUser loginUser(String email, String password, AppCompatActivity myActivity);
    void logoutUser();
    void loginAsGuest();
}
