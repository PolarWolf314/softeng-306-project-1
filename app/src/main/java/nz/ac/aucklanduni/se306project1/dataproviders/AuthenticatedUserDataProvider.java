package nz.ac.aucklanduni.se306project1.dataproviders;

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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import nz.ac.aucklanduni.se306project1.models.ShoppingCart;
import nz.ac.aucklanduni.se306project1.models.Watchlist;
import nz.ac.aucklanduni.se306project1.models.items.CartItem;
import nz.ac.aucklanduni.se306project1.models.items.SerializedCartItem;

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
    public void addToShoppingCart(final SerializedCartItem cartItem) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference shoppingCartRef = db.collection("shoppingCarts").document(this.user.getUid());

        shoppingCartRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    shoppingCartRef.update("items", FieldValue.arrayUnion(cartItem));
                } else {
                    Map<String, List<SerializedCartItem>> initialShoppingCart = new HashMap<>();
                    initialShoppingCart.put("itemIds", new ArrayList<>(Arrays.asList(cartItem)));
                    db.collection("watchlists").document(this.user.getUid()).set(initialShoppingCart);
                }
            }
        });
    }

    @Override
    public void removeFromShoppingCart(final SerializedCartItem cartItem) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference shoppingCartRef = db.collection("shoppingCarts").document(this.user.getUid());

        shoppingCartRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    shoppingCartRef.update("items", FieldValue.arrayRemove(cartItem));
                }
            }
        });
    }

    @Override
    public ShoppingCart getShoppingCart() throws ExecutionException, InterruptedException {
        CompletableFuture<ShoppingCart> myShoppingCart = new CompletableFuture<>();

        if (this.user == null) {
            throw new RuntimeException("User does not exist");
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference shoppingCartRef = db.collection("shoppingCarts").document(this.user.getUid());

        shoppingCartRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                Map<String, Object> myObject = document.getData();
                List<SerializedCartItem> firebaseCartItems = (List<SerializedCartItem>) myObject.get("items");
                List<CartItem> cartItems = new ArrayList<>();
                ItemDataProvider myItemProvider = new FirebaseItemDataProvider();
                for (SerializedCartItem firebaseCartItem : firebaseCartItems) {
                    cartItems.add(new CartItem(firebaseCartItem.getQuantity(), firebaseCartItem.getColour(),
                            firebaseCartItem.getSize(), myItemProvider.getItemById(firebaseCartItem.getItemId())));
                }
                myShoppingCart.complete(new ShoppingCart(document.getId(), cartItems));
            } else {
                // return empty shopping cart if user doesn't have one
                myShoppingCart.complete(new ShoppingCart(this.user.getUid(), new ArrayList<>()));
            }
        });

        return myShoppingCart.get();
    }

    @Override
    public Watchlist getWatchlist() throws ExecutionException, InterruptedException {
        CompletableFuture<Watchlist> myWatchlist = new CompletableFuture<>();

        if (this.user == null) {
            throw new RuntimeException("User does not exist");
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference watchlistRef = db.collection("watchlists").document(this.user.getUid());

        watchlistRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                Map<String, Object> myObject = document.getData();
                List<String> itemIds = (List<String>) myObject.get("itemIds");
                myWatchlist.complete(new Watchlist(document.getId(), itemIds));
            } else {
                // return empty watchlist if user doesn't have one
                myWatchlist.complete(new Watchlist(this.user.getUid(), new ArrayList<>()));
            }
        });

        return myWatchlist.get();
    }
}
