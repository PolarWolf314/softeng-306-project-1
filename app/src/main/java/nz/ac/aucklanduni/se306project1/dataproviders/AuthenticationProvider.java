package nz.ac.aucklanduni.se306project1.dataproviders;

import android.app.Activity;

import com.google.firebase.auth.FirebaseUser;

public interface AuthenticationProvider {

    UserDataProvider registerUser(String email, String password, Activity myActivity);

    UserDataProvider loginUser(String email, String password, Activity myActivity);
    void logoutUser();
}
