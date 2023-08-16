package nz.ac.aucklanduni.se306project1.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import nz.ac.aucklanduni.se306project1.R;
import nz.ac.aucklanduni.se306project1.adapters.ListRecyclerAdapter;
import nz.ac.aucklanduni.se306project1.data.MockData;
import nz.ac.aucklanduni.se306project1.databinding.ActivityHomeBinding;
import nz.ac.aucklanduni.se306project1.itemdecorations.ItemSpacingDecoration;
import nz.ac.aucklanduni.se306project1.models.items.Item;
import nz.ac.aucklanduni.se306project1.viewholders.FeaturedItemCardViewHolder;
import nz.ac.aucklanduni.se306project1.viewmodels.ItemSearchViewModel;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private ItemSearchViewModel searchViewModel;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = ActivityHomeBinding.inflate(this.getLayoutInflater());
        this.setContentView(this.binding.getRoot());

        this.searchViewModel = new ViewModelProvider(this).get(ItemSearchViewModel.class);
        this.searchViewModel.setOriginalItems(MockData.ITEMS);

        final RecyclerView recyclerView = this.binding.featuredProductsRecyclerView;
        final ListRecyclerAdapter<Item, ?> adapter = new ListRecyclerAdapter<>(
                this, this.searchViewModel.getFilteredItems(), FeaturedItemCardViewHolder.Builder.INSTANCE);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        final int horizontalSpacingInPixels = this.getResources().getDimensionPixelSize(R.dimen.horizontal_item_spacing);
        recyclerView.addItemDecoration(new ItemSpacingDecoration(this, horizontalSpacingInPixels));

        final Intent intent = new Intent(this, ListActivity.class);

        this.binding.civilCategory.setOnClickListener(v -> {
            // Create intent and navigate to ListActivity
            this.startActivity(intent);
        });

        this.binding.softwareCategory.setOnClickListener(v -> {
            // Create intent and navigate to ListActivity
            this.startActivity(intent);
        });

        this.binding.chemmatCategory.setOnClickListener(v -> {
            // Create intent and navigate to ListActivity
            this.startActivity(intent);
        });

        this.binding.mechanicalCategory.setOnClickListener(v -> {
            // Create intent and navigate to ListActivity
            this.startActivity(intent);
        });

    }
}