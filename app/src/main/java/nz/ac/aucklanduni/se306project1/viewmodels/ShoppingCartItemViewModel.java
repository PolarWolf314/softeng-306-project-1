package nz.ac.aucklanduni.se306project1.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import nz.ac.aucklanduni.se306project1.dataproviders.UserDataProvider;
import nz.ac.aucklanduni.se306project1.models.items.CartItem;
import nz.ac.aucklanduni.se306project1.models.items.SerializedCartItem;

public class ShoppingCartItemViewModel extends ViewModel {

    protected final MutableLiveData<Set<CartItem>> shoppingCartItems = new MutableLiveData<>(Collections.emptySet());

    protected final MutableLiveData<Double> totalPrice = new MutableLiveData<>(0.0);

    protected final UserDataProvider userDataProvider;

    public ShoppingCartItemViewModel(final UserDataProvider userDataProvider) {
        this.userDataProvider = userDataProvider;
        this.userDataProvider.getShoppingCart().thenAccept(cartItems -> {
            this.shoppingCartItems.setValue(cartItems);
            Double tempPrice = 0.0;
            for (CartItem cartItem : this.shoppingCartItems.getValue()) {
                tempPrice += (cartItem.getQuantity() * cartItem.getItem().getPrice());
            }
            this.totalPrice.setValue(tempPrice);
        });
    }

    public void incrementItemQuantity(final CartItem cartItem) {
        this.userDataProvider.incrementShoppingCartItemQuantity(new SerializedCartItem(cartItem.getQuantity(),
                cartItem.getColour(), cartItem.getSize(), cartItem.getItem().getId()));
        cartItem.incrementQuantity();
        this.totalPrice.setValue(this.totalPrice.getValue() + cartItem.getItem().getPrice());
    }

    public void decrementItemQuantity(final CartItem cartItem) {
        this.userDataProvider.decrementShoppingCartItemQuantity(new SerializedCartItem(cartItem.getQuantity(),
                cartItem.getColour(), cartItem.getSize(), cartItem.getItem().getId()));
        cartItem.decrementQuantity();
        this.totalPrice.setValue(this.totalPrice.getValue() - cartItem.getItem().getPrice());
    }

    public void changeItemQuantity(final CartItem cartItem, int newQuantity) {
        this.totalPrice.setValue(this.totalPrice.getValue() + ((newQuantity - cartItem.getQuantity()) * cartItem.getItem().getPrice()));
        this.userDataProvider.changeShoppingCartItemQuantity(new SerializedCartItem(cartItem.getQuantity(),
                cartItem.getColour(), cartItem.getSize(), cartItem.getItem().getId()), newQuantity);
        cartItem.changeQuantity(newQuantity);
    }

    public void removeItemFromCart(final CartItem cartItem) {
        this.userDataProvider.removeFromShoppingCart(new SerializedCartItem(cartItem.getQuantity(),
                cartItem.getColour(), cartItem.getSize(), cartItem.getItem().getId()));
        Set<CartItem> cartItems = this.getCartItems();
        cartItems.remove(cartItem);
        this.shoppingCartItems.setValue(cartItems);
        this.totalPrice.setValue(this.totalPrice.getValue() - cartItem.getCollectivePrice());
    }

    public LiveData<Double> getTotalPrice() {
        return this.totalPrice;
    }

    private Set<CartItem> getCartItems() {
        final Set<CartItem> items = this.shoppingCartItems.getValue();
        if (items == null) return new HashSet<>();
        return items;
    }
}
