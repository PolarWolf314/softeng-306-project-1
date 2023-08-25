package nz.ac.aucklanduni.se306project1.dataproviders;

import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import java.util.concurrent.CompletableFuture;

import nz.ac.aucklanduni.se306project1.exceptions.EmailAlreadyInUseException;
import nz.ac.aucklanduni.se306project1.exceptions.InvalidEmailException;
import nz.ac.aucklanduni.se306project1.exceptions.WeakPasswordException;
import nz.ac.aucklanduni.se306project1.utils.FutureUtils;

public class FirebaseAuthenticationProvider implements AuthenticationProvider {
    private final FirebaseAuth auth;
    private final ItemDataProvider itemDataProvider;

    @Nullable
    private AuthenticatedUserDataProvider userDataProvider;

    public FirebaseAuthenticationProvider(final ItemDataProvider itemDataProvider) {
        this.auth = FirebaseAuth.getInstance();
        this.itemDataProvider = itemDataProvider;
    }

    /**
     * This method creates an account in Firebase using Firebase authentication. This account
     * creation is performed asynchronously, and hence <code>thenApply()</code> and
     * <code>thenAccept()</code> need to also be used when invoking this method.
     *
     * @param email    The user's email to be registered
     * @param password The user's password to be registered
     * @return A {@link CompletableFuture} object that contains an instance of UserDataProvider associated
     * with the user
     */
    @Override
    public CompletableFuture<UserDataProvider> registerUser(final String email, final String password) {
        return FutureUtils.fromTask(this.auth.createUserWithEmailAndPassword(email, password))
                .thenApply(authResult -> {
                    this.userDataProvider = new AuthenticatedUserDataProvider(this.itemDataProvider, authResult.getUser());
                    return (UserDataProvider) this.userDataProvider;
                }).exceptionally(exception -> {
                    Class<?> exceptionClass = exception.getCause().getClass();
                    if (exceptionClass.equals(FirebaseAuthUserCollisionException.class)) {
                        throw new EmailAlreadyInUseException();
                    } else if (exceptionClass.equals(FirebaseAuthWeakPasswordException.class)) {
                        throw new WeakPasswordException();
                    } else if (exceptionClass.equals(FirebaseAuthInvalidCredentialsException.class)) {
                        throw new InvalidEmailException();
                    }
                    throw new RuntimeException();
                });
    }

    /**
     * This method  verifies an email and password in Firebase using Firebase authentication. This
     * account creation is performed asynchronously, and hence <code>thenApply()</code> and
     * <code>thenAccept()</code> need to also be used when invoking this method.
     *
     * @param email    The user's email to be registered
     * @param password The user's password to be registered
     * @return A {@link CompletableFuture} object that contains an instance of UserDataProvider associated
     * with the user
     */
    @Override
    public CompletableFuture<UserDataProvider> loginUser(final String email, final String password) {
        return FutureUtils.fromTask(this.auth.signInWithEmailAndPassword(email, password))
                .thenApply(authResult -> {
                    this.userDataProvider = new AuthenticatedUserDataProvider(this.itemDataProvider, authResult.getUser());
                    return this.userDataProvider;
                });
    }

    @Override
    public UserDataProvider getCurrentUserDataProvider() {
        if (this.auth.getCurrentUser() != null) {
            this.userDataProvider = new AuthenticatedUserDataProvider(this.itemDataProvider, this.auth.getCurrentUser());
            return this.userDataProvider;
        } else {
            throw new RuntimeException("No user logged in");
        }
    }

    @Override
    public void logoutUser() {
        this.auth.signOut();
        if (this.userDataProvider != null) this.userDataProvider.removeUser();
    }
}
