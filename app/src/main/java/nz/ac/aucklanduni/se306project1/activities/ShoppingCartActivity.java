package nz.ac.aucklanduni.se306project1.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Set;

import nz.ac.aucklanduni.se306project1.R;
import nz.ac.aucklanduni.se306project1.adapters.ListRecyclerAdapter;
import nz.ac.aucklanduni.se306project1.data.Constants;
import nz.ac.aucklanduni.se306project1.databinding.ActivityShoppingCartBinding;
import nz.ac.aucklanduni.se306project1.itemdecorations.VerticalItemSpacingDecoration;
import nz.ac.aucklanduni.se306project1.models.items.CartItem;
import nz.ac.aucklanduni.se306project1.utils.StringUtils;
import nz.ac.aucklanduni.se306project1.viewholders.CartItemCardViewHolder;
import nz.ac.aucklanduni.se306project1.viewmodels.BottomNavigationViewModel;
import nz.ac.aucklanduni.se306project1.viewmodels.CartItemSearchViewModel;
import nz.ac.aucklanduni.se306project1.viewmodels.ShoppingCartViewModel;

public class ShoppingCartActivity extends AppCompatActivity {


    private ActivityShoppingCartBinding binding;
    private CartItemSearchViewModel searchViewModel;
    private ShoppingCartViewModel shoppingCartViewModel;
    private BottomNavigationViewModel bottomNavigationViewModel;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = ActivityShoppingCartBinding.inflate(this.getLayoutInflater());
        this.setContentView(this.binding.getRoot());

        this.searchViewModel = new ViewModelProvider(this).get(CartItemSearchViewModel.class);
        this.shoppingCartViewModel = new ViewModelProvider(this, ViewModelProvider.Factory.from(ShoppingCartViewModel.initializer)).get(ShoppingCartViewModel.class);
        this.shoppingCartViewModel.getShoppingCartItems().observe(this, this::onShoppingCartItemsLoaded);
        this.shoppingCartViewModel.getTotalPrice().observe(this, this::onTotalPriceChange);

        final RecyclerView recyclerView = this.binding.cartRecyclerView;

        final ListRecyclerAdapter<CartItem, ?> adapter = new ListRecyclerAdapter<>(
                this.getApplication(), this.searchViewModel.getFilteredItems(),
                new CartItemCardViewHolder.Builder(this.shoppingCartViewModel));

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new VerticalItemSpacingDecoration(this, 12));

        this.binding.clearCartButton.setOnClickListener(v -> this.shoppingCartViewModel.clearShoppingCart());

        this.binding.checkoutButton.setOnClickListener(v -> {
            this.shoppingCartViewModel.checkout();
            Toast.makeText(this, Constants.ToastMessages.CHECKOUT_MESSAGE, Toast.LENGTH_LONG).show();
        });

        this.bottomNavigationViewModel = new ViewModelProvider(this).get(BottomNavigationViewModel.class);
        this.bottomNavigationViewModel.setSelectedItemId(R.id.navigation_cart);

        this.getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.red_top_bar));
        this.getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.background_light_gray));
    }

    private void onShoppingCartItemsLoaded(final Set<CartItem> shoppingCartItems) {
        this.searchViewModel.setOriginalItems(new ArrayList<>(shoppingCartItems));
        final String label = StringUtils.getQuantity(
                this.getResources(), R.plurals.number_of_items_in_cart,
                R.string.no_items_in_cart, shoppingCartItems.size());
        this.binding.cartItemCount.setText(label);
    }

    private void onTotalPriceChange(final Double totalPrice) {
        this.binding.cartTotalPrice.setText(String.format(Locale.getDefault(), "$%.2f", totalPrice));
    }
}