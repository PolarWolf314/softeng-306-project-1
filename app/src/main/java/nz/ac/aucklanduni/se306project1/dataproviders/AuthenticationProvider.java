package nz.ac.aucklanduni.se306project1.dataproviders;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface AuthenticationProvider {

    CompletableFuture<Optional<UserDataProvider>> registerUser(String email, String password);

    CompletableFuture<Optional<UserDataProvider>> loginUser(String email, String password);

    void logoutUser();
}
