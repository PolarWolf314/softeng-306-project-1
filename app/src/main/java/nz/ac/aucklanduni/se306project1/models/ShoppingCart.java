package nz.ac.aucklanduni.se306project1.models;

import java.util.Collections;
import java.util.List;

import nz.ac.aucklanduni.se306project1.models.items.CartItem;

public class ShoppingCart {
    private String userId;
    private List<CartItem> cartItems;

    public ShoppingCart() {
    }

    public ShoppingCart(final String userId, final List<CartItem> cartItems) {
        this.userId = userId;
        this.cartItems = cartItems;
    }

    public void clearCart() {
        this.cartItems.clear();
    }

    public void addItem(final CartItem cartItem) {
        this.cartItems.add(cartItem);
    }

    public void removeItem(final CartItem cartItem) {
        this.cartItems.remove(cartItem);
    }

    public String getUserId() {
        return this.userId;
    }

    public List<CartItem> getCartItems() {
        return Collections.unmodifiableList(this.cartItems);
    }
}
