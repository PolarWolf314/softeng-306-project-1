package nz.ac.aucklanduni.se306project1.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.function.Predicate;

import nz.ac.aucklanduni.se306project1.R;
import nz.ac.aucklanduni.se306project1.adapters.ListRecyclerAdapter;
import nz.ac.aucklanduni.se306project1.data.MockData;
import nz.ac.aucklanduni.se306project1.databinding.ActivityHomeBinding;
import nz.ac.aucklanduni.se306project1.itemdecorations.HorizontalItemSpacingDecoration;
import nz.ac.aucklanduni.se306project1.models.items.Item;
import nz.ac.aucklanduni.se306project1.viewholders.FeaturedItemCardViewHolder;
import nz.ac.aucklanduni.se306project1.viewmodels.ItemSearchViewModel;

public class HomeActivity extends TopBarActivity {

    private ActivityHomeBinding binding;
    private ItemSearchViewModel searchViewModel;
    private boolean hasFilter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = ActivityHomeBinding.inflate(this.getLayoutInflater());
        this.setContentView(this.binding.getRoot());

        this.searchViewModel = new ViewModelProvider(this).get(ItemSearchViewModel.class);
        this.searchViewModel.setOriginalItemsIfEmpty(MockData.ITEMS);

        final RecyclerView recyclerView = this.binding.featuredProductsRecyclerView;
        final ListRecyclerAdapter<Item, ?> adapter = new ListRecyclerAdapter<>(
                this, this.searchViewModel.getFilteredItems(), FeaturedItemCardViewHolder.Builder.INSTANCE);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        final int horizontalSpacingInPixels = this.getResources().getDimensionPixelSize(R.dimen.horizontal_item_spacing);
        recyclerView.addItemDecoration(new HorizontalItemSpacingDecoration(this, horizontalSpacingInPixels));

        final Intent intent = new Intent(this, ListActivity.class);
        this.binding.civilCategory.setOnClickListener(v -> this.startActivity(intent));
        this.binding.softwareCategory.setOnClickListener(v -> this.startActivity(intent));
        this.binding.chemmatCategory.setOnClickListener(v -> this.startActivity(intent));
        this.binding.mechanicalCategory.setOnClickListener(v -> this.startActivity(intent));

        // We have to keep a reference to the specific filter we're adding.
        final Predicate<Item> filter = this::isPriceGreaterThan20Dollars;

        this.binding.toggleFilterButton.setOnClickListener(view -> {
            if (this.hasFilter) {
                this.searchViewModel.removeFilter(filter);
            } else {
                this.searchViewModel.addFilter(filter);
            }
            this.hasFilter = !this.hasFilter;
        });
    }

    public boolean isPriceGreaterThan20Dollars(final Item item) {
        return item.getPrice() > 20D;
    }
}