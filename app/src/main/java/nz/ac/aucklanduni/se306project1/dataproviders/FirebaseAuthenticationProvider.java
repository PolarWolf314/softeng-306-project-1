package nz.ac.aucklanduni.se306project1.dataproviders;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

public class FirebaseAuthenticationProvider implements AuthenticationProvider {
    private FirebaseAuth mAuth;

    @Override
    public FirebaseUser registerUser(String email, String password) {
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser[] registeredUser = new FirebaseUser[1];
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Executor) this, task -> {
                    if (task.isSuccessful()) {
                        registeredUser[0] = mAuth.getCurrentUser();
                    } else {
                        throw new RuntimeException("Error in user registration");
                    }
                });
        return registeredUser[0];
    }

    @Override
    public FirebaseUser loginUser(String email, String password) {
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser[] registeredUser = new FirebaseUser[1];
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Executor) this, task -> {
                    if (task.isSuccessful()) {
                        registeredUser[0] = mAuth.getCurrentUser();
                    } else {
                        throw new RuntimeException("Error in user login");
                    }
                });
        return registeredUser[0];
    }

    @Override
    public void logoutUser() {
        mAuth.signOut();
    }

    @Override
    public void loginAsGuest() {

    }
}
