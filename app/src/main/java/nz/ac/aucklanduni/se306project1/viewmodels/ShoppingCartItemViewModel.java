package nz.ac.aucklanduni.se306project1.viewmodels;

import androidx.lifecycle.MutableLiveData;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import nz.ac.aucklanduni.se306project1.dataproviders.UserDataProvider;
import nz.ac.aucklanduni.se306project1.models.items.CartItem;
import nz.ac.aucklanduni.se306project1.models.items.Item;
import nz.ac.aucklanduni.se306project1.models.items.SerializedCartItem;

public class ShoppingCartItemViewModel extends ItemSearchViewModel {

    protected final MutableLiveData<Set<CartItem>> shoppingCartItems = new MutableLiveData<>(Collections.emptySet());

    protected final UserDataProvider userDataProvider;

    public ShoppingCartItemViewModel(final UserDataProvider userDataProvider) {
        this.userDataProvider = userDataProvider;
        this.userDataProvider.getShoppingCart().thenAccept(this.shoppingCartItems::setValue);
    }

    public void incrementItemQuantity(final CartItem cartItem) {
        this.userDataProvider.incrementShoppingCartItemQuantity(new SerializedCartItem(cartItem.getQuantity(),
                cartItem.getColour(), cartItem.getSize(), cartItem.getItem().getId()));
        Set<CartItem> cartItems = this.getCartItems();
        cartItems.remove(cartItem);
        cartItem.incrementQuantity();
        cartItems.add(cartItem);
        this.shoppingCartItems.setValue(cartItems);
    }

    public void decrementItemQuantity(final CartItem cartItem) {
        this.userDataProvider.decrementShoppingCartItemQuantity(new SerializedCartItem(cartItem.getQuantity(),
                cartItem.getColour(), cartItem.getSize(), cartItem.getItem().getId()));
        Set<CartItem> cartItems = this.getCartItems();
        cartItems.remove(cartItem);
        cartItem.decrementQuantity();
        cartItems.add(cartItem);
        this.shoppingCartItems.setValue(cartItems);
    }

    public void changeItemQuantity(final CartItem cartItem, int newQuantity) {
        this.userDataProvider.changeShoppingCartItemQuantity(new SerializedCartItem(cartItem.getQuantity(),
                cartItem.getColour(), cartItem.getSize(), cartItem.getItem().getId()), newQuantity);
        Set<CartItem> cartItems = this.getCartItems();
        cartItems.remove(cartItem);
        cartItem.changeQuantity(newQuantity);
        cartItems.add(cartItem);
        this.shoppingCartItems.setValue(cartItems);
    }

    private Set<CartItem> getCartItems() {
        final Set<CartItem> items = this.shoppingCartItems.getValue();
        if (items == null) return new HashSet<>();
        return items;
    }
}
