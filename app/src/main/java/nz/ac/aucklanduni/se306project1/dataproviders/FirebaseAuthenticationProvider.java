package nz.ac.aucklanduni.se306project1.dataproviders;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import nz.ac.aucklanduni.se306project1.utils.FutureUtils;

public class FirebaseAuthenticationProvider implements AuthenticationProvider {
    private final FirebaseAuth auth;

    public FirebaseAuthenticationProvider(final FirebaseAuth auth) {
        this.auth = auth;
    }

    @Override
    public CompletableFuture<Optional<UserDataProvider>> registerUser(final String email, final String password) {
        return FutureUtils.fromTask(this.auth.createUserWithEmailAndPassword(email, password))
                .thenApply(optional -> optional.map(
                        authResult -> new AuthenticatedUserDataProvider(authResult.getUser())));
    }

    @Override
    public CompletableFuture<Optional<UserDataProvider>> loginUser(final String email, final String password) {
        return FutureUtils.fromTask(this.auth.signInWithEmailAndPassword(email, password))
                .thenApply(optional -> optional.map(
                        authResult -> new AuthenticatedUserDataProvider(authResult.getUser())));
    }

    @Override
    public void logoutUser() {
        this.auth.signOut();
    }
}
