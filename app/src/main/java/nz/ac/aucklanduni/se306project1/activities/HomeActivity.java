package nz.ac.aucklanduni.se306project1.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import nz.ac.aucklanduni.se306project1.R;
import nz.ac.aucklanduni.se306project1.adapters.ListRecyclerAdapter;
import nz.ac.aucklanduni.se306project1.data.Constants;
import nz.ac.aucklanduni.se306project1.data.MockData;
import nz.ac.aucklanduni.se306project1.databinding.ActivityHomeBinding;
import nz.ac.aucklanduni.se306project1.itemdecorations.HorizontalItemSpacingDecoration;
import nz.ac.aucklanduni.se306project1.models.items.Item;
import nz.ac.aucklanduni.se306project1.viewholders.FeaturedItemCardViewHolder;
import nz.ac.aucklanduni.se306project1.viewmodels.ItemSearchViewModel;

public class HomeActivity extends TopBarActivity {

    private ActivityHomeBinding binding;
    private ItemSearchViewModel searchViewModel;

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

        this.binding.civilCategory.setOnClickListener(v -> this.switchToCategory("civil"));
        this.binding.softwareCategory.setOnClickListener(v -> this.switchToCategory("software"));
        this.binding.chemmatCategory.setOnClickListener(v -> this.switchToCategory("chemmat"));
        this.binding.mechanicalCategory.setOnClickListener(v -> this.switchToCategory("mechanical"));
    }

    private void switchToCategory(final String categoryId) {
        final Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra(Constants.IntentKeys.CATEGORY_ID, categoryId);
        this.startActivity(intent);
    }
}