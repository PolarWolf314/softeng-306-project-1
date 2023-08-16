package nz.ac.aucklanduni.se306project1.dataproviders;

import java.util.concurrent.ExecutionException;

import nz.ac.aucklanduni.se306project1.models.ShoppingCart;
import nz.ac.aucklanduni.se306project1.models.Watchlist;
import nz.ac.aucklanduni.se306project1.models.items.CartItem;
import nz.ac.aucklanduni.se306project1.models.items.SerializedCartItem;

public interface UserDataProvider {
    void addToWatchlist(final String itemId);

    void removeFromWatchlist(final String itemId);

    void addToShoppingCart(final SerializedCartItem cartItem);

    void removeFromShoppingCart(final SerializedCartItem cartItem);

    ShoppingCart getShoppingCart() throws ExecutionException, InterruptedException;

    Watchlist getWatchlist() throws ExecutionException, InterruptedException;
}
