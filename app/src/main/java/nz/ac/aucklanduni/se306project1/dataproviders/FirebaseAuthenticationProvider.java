package nz.ac.aucklanduni.se306project1.dataproviders;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseAuthenticationProvider implements AuthenticationProvider {
    private FirebaseAuth mAuth;
    private Context appContext;

    @Override
    public FirebaseUser registerUser(String email, String password, AppCompatActivity myActivity) {
        this.mAuth = FirebaseAuth.getInstance();
        this.appContext = myActivity;

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(myActivity, task -> {
                    if (!task.isSuccessful()) {
                        throw new RuntimeException("Error in user registration");
                    }
                });

        return this.mAuth.getCurrentUser();
    }

    @Override
    public FirebaseUser loginUser(String email, String password, AppCompatActivity myActivity) {
        this.mAuth = FirebaseAuth.getInstance();
        this.appContext = myActivity;

        this.mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(myActivity, task -> {
                    if (!task.isSuccessful()) {
                        throw new RuntimeException("Error in user login");
                    }
                });

        return this.mAuth.getCurrentUser();
    }

    @Override
    public void logoutUser() {
        this.mAuth.signOut();
    }

    @Override
    public void loginAsGuest() {

    }
}
