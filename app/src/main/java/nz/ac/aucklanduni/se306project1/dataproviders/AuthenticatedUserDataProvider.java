package nz.ac.aucklanduni.se306project1.dataproviders;

import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nz.ac.aucklanduni.se306project1.models.ShoppingCart;
import nz.ac.aucklanduni.se306project1.models.Watchlist;
import nz.ac.aucklanduni.se306project1.models.items.CartItem;

public class AuthenticatedUserDataProvider implements UserDataProvider {
    private FirebaseUser user;

    public AuthenticatedUserDataProvider(FirebaseUser appUser) {
        this.user = appUser;
    }

    @Override
    public void addToWatchlist(final String itemId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference watchlistRef = db.collection("watchlists").document(this.user.getUid());

        watchlistRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    watchlistRef.update("itemIds", FieldValue.arrayUnion(itemId));
                } else {
                    Map<String, List<String>> initialWatchlist = new HashMap<>();
                    initialWatchlist.put("itemIds", new ArrayList<>(Arrays.asList(itemId)));
                    db.collection("watchlists").document(this.user.getUid()).set(initialWatchlist);
                }
            } else {
                throw new RuntimeException("Error occurred while writing to Firestore watchlist");
            }
        });
    }

    @Override
    public void removeFromWatchlist(final String itemId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference watchlistRef = db.collection("watchlists").document(this.user.getUid());

        watchlistRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    watchlistRef.update("itemIds", FieldValue.arrayRemove(itemId));
                }
            } else {
                throw new RuntimeException("Error occurred while writing to Firestore watchlist");
            }
        });
    }

    @Override
    public void addToShoppingCart(CartItem cartItem) {

    }

    @Override
    public void removeFromShoppingCart(CartItem cartItem) {

    }

    @Override
    public ShoppingCart getShoppingCart() {
        return null;
    }

    @Override
    public Watchlist getWatchlist() {
        return null;
    }
}
