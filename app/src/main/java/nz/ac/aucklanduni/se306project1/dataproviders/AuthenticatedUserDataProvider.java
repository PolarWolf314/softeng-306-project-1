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

import nz.ac.aucklanduni.se306project1.models.ShoppingCart;
import nz.ac.aucklanduni.se306project1.models.Watchlist;
import nz.ac.aucklanduni.se306project1.models.items.CartItem;
import nz.ac.aucklanduni.se306project1.models.items.SerializedCartItem;
import nz.ac.aucklanduni.se306project1.utils.FutureUtils;

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
                    watchlistRef.set(initialWatchlist);
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
                    initialShoppingCart.put("items", new ArrayList<>(Arrays.asList(cartItem)));
                    shoppingCartRef.set(initialShoppingCart);
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
    public CompletableFuture<ShoppingCart> getShoppingCart() {
        CompletableFuture<ShoppingCart> myShoppingCart = new CompletableFuture<>();

        if (this.user == null) {
            throw new RuntimeException("User does not exist");
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference shoppingCartRef = db.collection("shoppingCarts").document(this.user.getUid());

        FutureUtils.fromTask(shoppingCartRef.get(), () -> new RuntimeException("User doesn't have shopping cart")).thenAccept(
                document -> {
                    List<Map<String, Object>> firebaseCartItems = (List<Map<String, Object>>) document.get("items");
                    List<CartItem> cartItems = new ArrayList<>();
                    ItemDataProvider myItemProvider = new FirebaseItemDataProvider();
                    Long quantity;
                    for (Map<String, Object> firebaseCartItem : firebaseCartItems) {
                        quantity = (Long) firebaseCartItem.get("quantity");
                        cartItems.add(new CartItem(quantity.intValue(), firebaseCartItem.get("colour").toString(),
                                firebaseCartItem.get("size").toString(), myItemProvider.getItemById(firebaseCartItem.get("itemId").toString())));
                    }
                    myShoppingCart.complete(new ShoppingCart(document.getId(), cartItems));
                }
        ).exceptionally(exception -> {
            // return empty shopping cart if the user doesn't have one
            myShoppingCart.complete(new ShoppingCart(this.user.getUid(), new ArrayList<>()));
            return null;
        });

        return myShoppingCart;
    }

    @Override
    public CompletableFuture<Watchlist> getWatchlist() {
        CompletableFuture<Watchlist> myWatchlist = new CompletableFuture<>();

        if (this.user == null) {
            throw new RuntimeException("User does not exist");
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference watchlistRef = db.collection("watchlists").document(this.user.getUid());

        FutureUtils.fromTask(watchlistRef.get(), () -> new RuntimeException("User doesn't have watchlist")).thenAccept(
                document -> {
                    Map<String, Object> myObject = document.getData();
                    List<String> itemIds = (List<String>) myObject.get("itemIds");
                    myWatchlist.complete(new Watchlist(document.getId(), itemIds));
                }
        ).exceptionally(
                exception -> {
                    // return empty watchlist if user doesn't have one
                    myWatchlist.complete(new Watchlist(this.user.getUid(), new ArrayList<>()));
                    return null;
                }
        );

        return myWatchlist;
    }
}
