package nz.ac.aucklanduni.se306project1.dataproviders;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;

public class FirebaseAuthenticationProvider implements AuthenticationProvider {
    private FirebaseAuth mAuth;
    @Nullable
    private Context context;

    @Override
    public UserDataProvider registerUser(String email, String password, Activity myActivity) {
        this.mAuth = FirebaseAuth.getInstance();
        this.context = myActivity;

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
        this.context = myActivity;

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
