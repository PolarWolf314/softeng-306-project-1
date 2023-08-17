package nz.ac.aucklanduni.se306project1.utils;

import com.google.android.gms.tasks.Task;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class FutureUtils {
    public static <T> CompletableFuture<Optional<T>> fromTask(final Task<T> task) {
        final CompletableFuture<Optional<T>> completableFuture = new CompletableFuture<>();
        task.addOnCompleteListener(t -> {
            if (t.isSuccessful()) {
                completableFuture.complete(Optional.of(t.getResult()));
            } else {
                completableFuture.complete(Optional.empty());
            }
        });
        return completableFuture;
    }
}
