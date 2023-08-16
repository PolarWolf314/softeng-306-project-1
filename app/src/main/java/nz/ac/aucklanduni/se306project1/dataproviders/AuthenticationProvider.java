package nz.ac.aucklanduni.se306project1.dataproviders;

import android.app.Activity;

import com.google.firebase.auth.FirebaseUser;

public interface AuthenticationProvider {

    FirebaseUser registerUser(String email, String password, Activity myActivity);

    FirebaseUser loginUser(String email, String password, Activity myActivity);
    void logoutUser();
    void loginAsGuest();

    FirebaseUser getUser();
}
