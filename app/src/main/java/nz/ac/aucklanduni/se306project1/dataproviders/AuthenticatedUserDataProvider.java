package nz.ac.aucklanduni.se306project1.dataproviders;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import nz.ac.aucklanduni.se306project1.models.Order;
import nz.ac.aucklanduni.se306project1.models.ShoppingCart;
import nz.ac.aucklanduni.se306project1.models.Watchlist;
import nz.ac.aucklanduni.se306project1.models.items.CartItem;
import nz.ac.aucklanduni.se306project1.models.items.Item;
import nz.ac.aucklanduni.se306project1.models.items.SerializedCartItem;
import nz.ac.aucklanduni.se306project1.utils.FutureUtils;

public class AuthenticatedUserDataProvider implements UserDataProvider {
    private final ItemDataProvider itemDataProvider;
    private FirebaseUser user;

    public AuthenticatedUserDataProvider(final ItemDataProvider itemDataProvider, final FirebaseUser user) {
        this.itemDataProvider = itemDataProvider;
        this.user = user;
    }

    @Override
    public void placeOrder(final Order order) {
        if (this.user == null) {
            throw new RuntimeException("User does not exist");
        }

        final FirebaseFirestore db = FirebaseFirestore.getInstance();

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

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final DocumentReference watchlistRef = db.collection("watchlists").document(this.user.getUid());

        watchlistRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                final DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    watchlistRef.update("itemIds", FieldValue.arrayUnion(itemId));
                } else {
                    watchlistRef.set(new Watchlist(new ArrayList<>(Collections.singletonList(itemId))));
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

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final DocumentReference watchlistRef = db.collection("watchlists").document(this.user.getUid());

        watchlistRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                final DocumentSnapshot document = task.getResult();
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

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final DocumentReference shoppingCartRef = db.collection("shoppingCarts").document(this.user.getUid());

        shoppingCartRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                final DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    shoppingCartRef.update("items", FieldValue.arrayUnion(cartItem));
                } else {
                    final Map<String, List<SerializedCartItem>> initialShoppingCart = new HashMap<>();
                    initialShoppingCart.put("items", new ArrayList<>(Collections.singletonList(cartItem)));
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

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final DocumentReference shoppingCartRef = db.collection("shoppingCarts").document(this.user.getUid());

        shoppingCartRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                final DocumentSnapshot document = task.getResult();
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
    public void changeShoppingCartItemQuantity(final SerializedCartItem cartItem, final int newQuantity) {
        if (this.user == null) {
            throw new RuntimeException("User does not exist");
        }

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final DocumentReference shoppingCartRef = db.collection("shoppingCarts").document(this.user.getUid());

        shoppingCartRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                final DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    final List<Object> shoppingCart = (List<Object>) document.get("items");
                    Map<String, Object> currentItem;
                    final SerializedCartItem newCartItem;
                    for (final Object shoppingCartItem : shoppingCart) {
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

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final DocumentReference shoppingCartRef = db.collection("shoppingCarts").document(this.user.getUid());

        shoppingCartRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                final DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    shoppingCartRef.update("items", new ArrayList<>());
                }
            } else {
                throw new RuntimeException("Error occurred while writing to Firestore shopping cart");
            }
        });
    }

    @Override
    public void clearWatchlist() {
        if (this.user == null) {
            throw new RuntimeException("User does not exist");
        }

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final DocumentReference shoppingCartRef = db.collection("watchlists").document(this.user.getUid());

        shoppingCartRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                final DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    shoppingCartRef.update("itemIds", new ArrayList<>());
                }
            } else {
                throw new RuntimeException("Error occurred while writing to Firestore watchlist");
            }
        });
    }

    @Override
    public CompletableFuture<ShoppingCart> getShoppingCart() {
        if (this.user == null) {
            throw new RuntimeException("User does not exist");
        }

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final DocumentReference shoppingCartRef = db.collection("shoppingCarts").document(this.user.getUid());

        return FutureUtils.fromTask(shoppingCartRef.get(), () -> new RuntimeException("User doesn't have shopping cart")).thenCompose(
                document -> {
                    final List<Map<String, Object>> firebaseCartItems = (List<Map<String, Object>>) document.get("items");
                    final List<CartItem> cartItems = new ArrayList<>();

                    // Keep an array of futures so that we can wait for them all to be finished
                    final CompletableFuture<?>[] futures = new CompletableFuture[firebaseCartItems.size()];

                    for (int i = 0; i < firebaseCartItems.size(); i++) {
                        final Map<String, Object> firebaseCartItem = firebaseCartItems.get(i);
                        final Long quantity = (Long) firebaseCartItem.get("quantity");

                        futures[i] = this.itemDataProvider.getItemById(firebaseCartItem.get("itemId").toString())
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
    public CompletableFuture<Set<Item>> getWatchlist() {
        if (this.user == null) {
            throw new RuntimeException("User does not exist");
        }

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final DocumentReference watchlistRef = db.collection("watchlists").document(this.user.getUid());

        return FutureUtils.fromTask(watchlistRef.get(), () -> new RuntimeException("User doesn't have watchlist"))
                .thenCompose(document -> {
                    final Set<Item> items = new HashSet<>();
                    final Watchlist watchlist = document.toObject(Watchlist.class);
                    final List<String> itemIds = watchlist.getItemIds();

                    final CompletableFuture<?>[] futures = new CompletableFuture[itemIds.size()];
                    for (int i = 0; i < itemIds.size(); i++) {
                        futures[i] = this.itemDataProvider.getItemById(itemIds.get(i))
                                .thenAccept(items::add);
                    }
                    return CompletableFuture.allOf(futures).thenApply(nothing -> items);
                })
                .exceptionally(exception -> new HashSet<>());
    }

    public void removeUser() {
        this.user = null;
    }
}
