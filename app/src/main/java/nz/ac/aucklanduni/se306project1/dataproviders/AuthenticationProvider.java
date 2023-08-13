package nz.ac.aucklanduni.se306project1.dataproviders;

import com.google.firebase.auth.FirebaseUser;

public interface AuthenticationProvider {

    FirebaseUser registerUser(String email, String password);

    FirebaseUser loginUser(String email, String password);
    void logoutUser();
    void loginAsGuest();
}
