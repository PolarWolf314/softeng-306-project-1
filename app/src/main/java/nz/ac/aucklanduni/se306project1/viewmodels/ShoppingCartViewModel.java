package nz.ac.aucklanduni.se306project1.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import nz.ac.aucklanduni.se306project1.EngiWearApplication;
import nz.ac.aucklanduni.se306project1.dataproviders.UserDataProvider;
import nz.ac.aucklanduni.se306project1.models.Order;
import nz.ac.aucklanduni.se306project1.models.items.CartItem;
import nz.ac.aucklanduni.se306project1.models.items.SerializedCartItem;

public class ShoppingCartViewModel extends ShoppingCartItemViewModel {
    public static final ViewModelInitializer<ShoppingCartViewModel> initializer = new ViewModelInitializer<>(
            ShoppingCartViewModel.class,
            creationExtras -> {
                final EngiWearApplication app = (EngiWearApplication) creationExtras.get(ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY);
                assert app != null;
                return new ShoppingCartViewModel(app.getUserDataProvider());
            });

    public ShoppingCartViewModel(final UserDataProvider userDataProvider) {
        super(userDataProvider);
    }

    public LiveData<Set<CartItem>> getShoppingCartItems() {
        return this.shoppingCartItems;
    }

    public void clearShoppingCart() {
        this.userDataProvider.clearShoppingCart();
        this.shoppingCartItems.setValue(new HashSet<>());
        this.totalPrice.setValue(0.0);
    }

    public void checkout() {
        List<SerializedCartItem> orderItems = this.shoppingCartItems.getValue().stream().map(cartItem ->
                new SerializedCartItem(cartItem.getQuantity(), cartItem.getColour(),
                        cartItem.getSize(), cartItem.getItem().getId())).collect(Collectors.toList());
        this.userDataProvider.placeOrder(new Order(this.userDataProvider.getUserId(), LocalDateTime.now(), orderItems));
        this.clearShoppingCart();
    }
}
