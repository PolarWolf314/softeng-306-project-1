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

import nz.ac.aucklanduni.se306project1.models.Order;
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
    public void placeOrder(Order order) {
        if (this.user == null) {
            throw new RuntimeException("User does not exist");
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("orders").add(order).addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                throw new RuntimeException("Error processing order");
            }
        });
    }

    @Override
    public void addToWatchlist(final String itemId) {
        if (this.user == null) {
            throw new RuntimeException("User does not exist");
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference watchlistRef = db.collection("watchlists").document(this.user.getUid());

        watchlistRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    watchlistRef.update("itemIds", FieldValue.arrayUnion(itemId));
                } else {
                    watchlistRef.set(new Watchlist(new ArrayList<>(Arrays.asList(itemId))));
                }
            } else {
                throw new RuntimeException("Error occurred while writing to Firestore watchlist");
            }
        });
    }

    @Override
    public void removeFromWatchlist(final String itemId) {
        if (this.user == null) {
            throw new RuntimeException("User does not exist");
        }

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
        if (this.user == null) {
            throw new RuntimeException("User does not exist");
        }

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
            } else {
                throw new RuntimeException("Error occurred while writing to Firestore shopping cart");
            }
        });
    }

    @Override
    public void removeFromShoppingCart(final SerializedCartItem cartItem) {
        if (this.user == null) {
            throw new RuntimeException("User does not exist");
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference shoppingCartRef = db.collection("shoppingCarts").document(this.user.getUid());

        shoppingCartRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    shoppingCartRef.update("items", FieldValue.arrayRemove(cartItem));
                }
            } else {
                throw new RuntimeException("Error occurred while writing to Firestore shopping cart");
            }
        });
    }

    @Override
    public void incrementShoppingCartItemQuantity(final SerializedCartItem cartItem) {
        this.changeShoppingCartItemQuantity(cartItem, cartItem.getQuantity() + 1);
    }

    @Override
    public void decrementShoppingCartItemQuantity(final SerializedCartItem cartItem) {
        this.changeShoppingCartItemQuantity(cartItem, cartItem.getQuantity() - 1);
    }

    @Override
    public void changeShoppingCartItemQuantity(final SerializedCartItem cartItem, int newQuantity) {
        if (this.user == null) {
            throw new RuntimeException("User does not exist");
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference shoppingCartRef = db.collection("shoppingCarts").document(this.user.getUid());

        shoppingCartRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    List<Object> shoppingCart = (List<Object>) document.get("items");
                    Map<String, Object> currentItem;
                    SerializedCartItem newCartItem;
                    for (Object shoppingCartItem : shoppingCart) {
                        currentItem = (Map<String, Object>) shoppingCartItem;
                        if (currentItem.get("itemId").equals(cartItem.getItemId())) {
                            newCartItem = new SerializedCartItem(newQuantity, currentItem.get("colour").toString(),
                                    currentItem.get("size").toString(), currentItem.get("itemId").toString());
                            shoppingCartRef.update("items", FieldValue.arrayRemove(cartItem));
                            shoppingCartRef.update("items", FieldValue.arrayUnion(newCartItem));
                            break;
                        }
                    }
                }
            } else {
                throw new RuntimeException("Error occurred while writing to Firestore shopping cart");
            }
        });
    }

    @Override
    public void clearShoppingCart() {
        if (this.user == null) {
            throw new RuntimeException("User does not exist");
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference shoppingCartRef = db.collection("shoppingCarts").document(this.user.getUid());

        shoppingCartRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    shoppingCartRef.update("items", new ArrayList<>());
                }
            } else {
                throw new RuntimeException("Error occurred while writing to Firestore shopping cart");
            }
        });
    }

    @Override
    public CompletableFuture<ShoppingCart> getShoppingCart() {
        if (this.user == null) {
            throw new RuntimeException("User does not exist");
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference shoppingCartRef = db.collection("shoppingCarts").document(this.user.getUid());

        return FutureUtils.fromTask(shoppingCartRef.get(), () -> new RuntimeException("User doesn't have shopping cart")).thenCompose(
                document -> {
                    final List<Map<String, Object>> firebaseCartItems = (List<Map<String, Object>>) document.get("items");
                    final List<CartItem> cartItems = new ArrayList<>();
                    final ItemDataProvider myItemProvider = new FirebaseItemDataProvider();

                    // Keep an array of futures so that we can wait for them all to be finished
                    final CompletableFuture<Void>[] futures = new CompletableFuture[firebaseCartItems.size()];

                    for (int i = 0; i < firebaseCartItems.size(); i++) {
                        final Map<String, Object> firebaseCartItem = firebaseCartItems.get(i);
                        final Long quantity = (Long) firebaseCartItem.get("quantity");

                        futures[i] = myItemProvider.getItemById(firebaseCartItem.get("itemId").toString())
                                .thenAccept(item -> cartItems.add(new CartItem(
                                        quantity.intValue(),
                                        firebaseCartItem.get("colour").toString(),
                                        firebaseCartItem.get("size").toString(),
                                        item))
                                );
                    }

                    return CompletableFuture.allOf(futures).thenApply(nothing -> new ShoppingCart(cartItems));
                }
        ).exceptionally(exception -> new ShoppingCart(new ArrayList<>()));
    }

    @Override
    public CompletableFuture<Watchlist> getWatchlist() {
        if (this.user == null) {
            throw new RuntimeException("User does not exist");
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference watchlistRef = db.collection("watchlists").document(this.user.getUid());

        return FutureUtils.fromTask(watchlistRef.get(), () -> new RuntimeException("User doesn't have watchlist")).thenApply(
                document -> document.toObject(Watchlist.class)
        ).exceptionally(
                exception -> new Watchlist(new ArrayList<>())
        );
    }

    public void removeUser() {
        this.user = null;
    }
}
