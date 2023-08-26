package nz.ac.aucklanduni.se306project1.dataproviders;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

import nz.ac.aucklanduni.se306project1.models.Order;
import nz.ac.aucklanduni.se306project1.models.ShoppingCart;
import nz.ac.aucklanduni.se306project1.models.items.CartItem;
import nz.ac.aucklanduni.se306project1.models.items.Item;
import nz.ac.aucklanduni.se306project1.models.items.SerializedCartItem;

public interface UserDataProvider {
    void placeOrder(Order order);

    void addToWatchlist(final String itemId);

    void removeFromWatchlist(final String itemId);

    void addToShoppingCart(final SerializedCartItem cartItem);

    void removeFromShoppingCart(final SerializedCartItem cartItem);

    void incrementShoppingCartItemQuantity(final SerializedCartItem cartItem);

    void decrementShoppingCartItemQuantity(final SerializedCartItem cartItem);

    void changeShoppingCartItemQuantity(final SerializedCartItem cartItem, int quantity);

    void clearShoppingCart();

    void clearWatchlist();

    CompletableFuture<Set<CartItem>> getShoppingCart();

    CompletableFuture<Set<Item>> getWatchlist();
}
