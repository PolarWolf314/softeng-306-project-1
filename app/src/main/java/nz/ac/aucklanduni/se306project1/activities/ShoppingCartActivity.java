package nz.ac.aucklanduni.se306project1.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.stream.Collectors;

import nz.ac.aucklanduni.se306project1.adapters.ListRecyclerAdapter;
import nz.ac.aucklanduni.se306project1.data.MockData;
import nz.ac.aucklanduni.se306project1.databinding.ActivityShoppingCartBinding;
import nz.ac.aucklanduni.se306project1.models.items.CartItem;
import nz.ac.aucklanduni.se306project1.viewholders.CartItemCardViewHolder;
import nz.ac.aucklanduni.se306project1.viewmodels.ItemSearchViewModel;

public class ShoppingCartActivity extends AppCompatActivity {


    private ActivityShoppingCartBinding binding;
    private ItemSearchViewModel searchViewModel;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = ActivityShoppingCartBinding.inflate(this.getLayoutInflater());
        this.setContentView(this.binding.getRoot());

        this.searchViewModel = new ViewModelProvider(this).get(ItemSearchViewModel.class);
        this.searchViewModel.setOriginalItems(MockData.ITEMS);
        final RecyclerView recyclerView = this.binding.cartRecyclerView;

        final List<CartItem> cartItems = MockData.ITEMS.stream()
                .map(item -> new CartItem(1, item.getColours().get(0).getColour(), "m", item))
                .collect(Collectors.toList());

        final ListRecyclerAdapter<CartItem, ?> adapter = new ListRecyclerAdapter<>(
                this, new MutableLiveData<>(cartItems), CartItemCardViewHolder.Builder.INSTANCE);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

    }
}