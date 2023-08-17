package nz.ac.aucklanduni.se306project1.dataproviders;

import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.CompletableFuture;

import nz.ac.aucklanduni.se306project1.utils.FutureUtils;

public class FirebaseAuthenticationProvider implements AuthenticationProvider {
    private final FirebaseAuth auth;

    public FirebaseAuthenticationProvider() {
        this.auth = FirebaseAuth.getInstance();
    }

    /**
     * This method creates an account in Firebase using Firebase authentication. This account
     * creation is performed asynchronously, and hence thenApply() and thenAccept() need to also
     * be used when invoking this method.
     * @param email The user's email to be registered
     * @param password The user's password to be registered
     * @return A CompletableFuture object that contains an instance of UserDataProvider associated
     * with the user
     */
    @Override
    public CompletableFuture<UserDataProvider> registerUser(final String email, final String password) {
        return FutureUtils.fromTask(this.auth.createUserWithEmailAndPassword(email, password))
                .thenApply(authResult -> new AuthenticatedUserDataProvider(authResult.getUser()));
    }

    /**
     * This method  verifies an email and password in Firebase using Firebase authentication. This
     * account creation is performed asynchronously, and hence thenApply() and thenAccept() need to
     * also be used when invoking this method.
     * @param email The user's email to be registered
     * @param password The user's password to be registered
     * @return A CompletableFuture object that contains an instance of UserDataProvider associated
     * with the user
     */
    @Override
    public CompletableFuture<UserDataProvider> loginUser(final String email, final String password) {
        return FutureUtils.fromTask(this.auth.signInWithEmailAndPassword(email, password))
                .thenApply(authResult -> new AuthenticatedUserDataProvider(authResult.getUser()));
    }

    @Override
    public UserDataProvider getCurrentUserDataProvider() {
        if (this.auth.getCurrentUser() != null) {
            return new AuthenticatedUserDataProvider(this.auth.getCurrentUser());
        } else {
            throw new RuntimeException("No user logged in");
        }
    }

    /**
     * Logs out the user from Firebase, if they are logged in.
     */
    @Override
    public void logoutUser() {
        if (this.auth != null) this.auth.signOut();
    }
}
