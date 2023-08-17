package nz.ac.aucklanduni.se306project1.dataproviders;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface AuthenticationProvider {
    /**
     * This method registers a user in the database by taking the user's email and password. It
     * then returns a UserDataProvider that is used to fetch the data associated with the registered
     * user. Note that this method runs ASYNCHRONOUSLY and hence returns a
     * CompletableFuture<UserDataProvider> object. When invoking this method, the <code>thenApply()</code>
     * and <code>exceptionally()</code> methods should be called as well to handle the asynchronous nature
     * and use the returned data provider and/or exception.
     * @param email The user's email to be registered
     * @param password The user's password to be registered
     * @return A {@link CompletableFuture} object that contains an instance of UserDataProvider associated
     * with the user
     */
    CompletableFuture<UserDataProvider> registerUser(String email, String password);

    /**
     * This method logs in a user in the database by taking the user's email and password. It
     * then returns a UserDataProvider that is used to fetch the data associated with the registered
     * user. Note that this method runs ASYNCHRONOUSLY and hence returns a
     * CompletableFuture<UserDataProvider> object. When invoking this method, the <code>thenApply()</code>
     * and <code>exceptionally()</code> methods should be called as well to handle the asynchronous nature
     * and use the returned data provider and/or exception.
     * @param email The user's email to be registered
     * @param password The user's password to be registered
     * @return A {@link CompletableFuture} object that contains an instance of UserDataProvider associated
     * with the user
     */
    CompletableFuture<UserDataProvider> loginUser(String email, String password);

    /**
     * If the user is already logged into the app, then this method returns the appropriate instance
     * of UserDataProvider to fetch the data for the logged in user.
     * @return An instance of UserDataProvider for the logged in user
     * @throws RuntimeException if the user is not logged into the app
     */
    UserDataProvider getCurrentUserDataProvider();

    /**
     * Logs the user out of the app
     */
    void logoutUser();
}
