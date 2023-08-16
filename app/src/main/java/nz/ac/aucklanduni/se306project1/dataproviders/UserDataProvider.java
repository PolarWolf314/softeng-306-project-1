package nz.ac.aucklanduni.se306project1.dataproviders;

import nz.ac.aucklanduni.se306project1.models.ShoppingCart;
import nz.ac.aucklanduni.se306project1.models.Watchlist;
import nz.ac.aucklanduni.se306project1.models.items.CartItem;

public interface UserDataProvider {
    void addToWatchlist(final String itemId);

    void removeFromWatchlist(final String itemId);

    void addToShoppingCart(final CartItem cartItem);

    void removeFromShoppingCart(final CartItem cartItem);

    ShoppingCart getShoppingCart();

    Watchlist getWatchlist();
}
