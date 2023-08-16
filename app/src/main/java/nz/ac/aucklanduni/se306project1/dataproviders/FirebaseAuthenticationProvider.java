package nz.ac.aucklanduni.se306project1.dataproviders;

import android.app.Activity;
import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseAuthenticationProvider implements AuthenticationProvider {
    private FirebaseAuth mAuth;
    private Context appContext;

    @Override
    public UserDataProvider registerUser(String email, String password, Activity myActivity) {
        this.mAuth = FirebaseAuth.getInstance();
        this.appContext = myActivity;

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(myActivity, task -> {
                    if (!task.isSuccessful()) {
                        throw new RuntimeException("Error in user registration");
                    }
                });

        return new AuthenticatedUserDataProvider(this.mAuth.getCurrentUser());
    }

    @Override
    public UserDataProvider loginUser(String email, String password, Activity myActivity) {
        this.mAuth = FirebaseAuth.getInstance();
        this.appContext = myActivity;

        this.mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(myActivity, task -> {
                    if (!task.isSuccessful()) {
                        throw new RuntimeException("Error in user login");
                    }
                });

        return new AuthenticatedUserDataProvider(this.mAuth.getCurrentUser());
    }

    @Override
    public void logoutUser() {
        this.mAuth.signOut();
    }
}
