package nz.ac.aucklanduni.se306project1.models;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import nz.ac.aucklanduni.se306project1.models.items.CartItem;

public class ShoppingCart {
    private List<CartItem> cartItems;

    public ShoppingCart() {
    }

    public ShoppingCart(final List<CartItem> cartItems) {
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

    public List<CartItem> getCartItems() {
        return Collections.unmodifiableList(this.cartItems);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        final ShoppingCart that = (ShoppingCart) o;
        return Objects.equals(this.cartItems, that.cartItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.cartItems);
    }
}
