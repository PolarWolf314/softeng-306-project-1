package nz.ac.aucklanduni.se306project1.utils;

import com.google.android.gms.tasks.Task;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class FutureUtils {
    public static <T> CompletableFuture<T> fromTask(final Task<T> task) {
        final CompletableFuture<T> completableFuture = new CompletableFuture<>();
        task.addOnCompleteListener(t -> {
            if (t.isSuccessful()) {
                completableFuture.complete(t.getResult());
            } else {
                completableFuture.completeExceptionally(new RuntimeException("Error in task"));
            }
        });
        return completableFuture;
    }
}
