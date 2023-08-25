package nz.ac.aucklanduni.se306project1.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import nz.ac.aucklanduni.se306project1.R;
import nz.ac.aucklanduni.se306project1.adapters.ListRecyclerAdapter;
import nz.ac.aucklanduni.se306project1.data.Constants;
import nz.ac.aucklanduni.se306project1.databinding.ActivityHomeBinding;
import nz.ac.aucklanduni.se306project1.itemdecorations.HorizontalItemSpacingDecoration;
import nz.ac.aucklanduni.se306project1.models.enums.Category;
import nz.ac.aucklanduni.se306project1.models.items.Item;
import nz.ac.aucklanduni.se306project1.viewholders.FeaturedItemCardViewHolderBuilder;
import nz.ac.aucklanduni.se306project1.viewmodels.BottomNavigationViewModel;
import nz.ac.aucklanduni.se306project1.viewmodels.HomeViewModel;
import nz.ac.aucklanduni.se306project1.viewmodels.ItemSearchViewModel;

public class HomeActivity extends TopBarActivity {

    private ActivityHomeBinding binding;
    private HomeViewModel homeViewModel;
    private ItemSearchViewModel searchViewModel;
    private BottomNavigationViewModel bottomNavigationViewModel;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final int NUM_FEATURED_ITEMS = 5;

        this.binding = ActivityHomeBinding.inflate(this.getLayoutInflater());
        this.setContentView(this.binding.getRoot());

        this.homeViewModel = new ViewModelProvider(this, ViewModelProvider.Factory.from(HomeViewModel.initializer)).get(HomeViewModel.class);
        this.searchViewModel = new ViewModelProvider(this).get(ItemSearchViewModel.class);
        this.homeViewModel.getFeaturedItems(NUM_FEATURED_ITEMS).thenAccept(items -> this.searchViewModel.setOriginalItems(items));

        final RecyclerView recyclerView = this.binding.featuredProductsRecyclerView;
        final ListRecyclerAdapter<Item, ?> adapter = new ListRecyclerAdapter<>(
                this, this.searchViewModel.getFilteredItems(), new FeaturedItemCardViewHolderBuilder(this.homeViewModel));

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        final int horizontalSpacingInPixels = this.getResources().getDimensionPixelSize(R.dimen.horizontal_item_spacing);
        recyclerView.addItemDecoration(new HorizontalItemSpacingDecoration(this, horizontalSpacingInPixels));

        this.binding.civilCategory.setOnClickListener(v -> this.switchToCategory(Category.CIVIL));
        this.binding.softwareCategory.setOnClickListener(v -> this.switchToCategory(Category.SOFTWARE));
        this.binding.chemmatCategory.setOnClickListener(v -> this.switchToCategory(Category.CHEMMAT));
        this.binding.mechanicalCategory.setOnClickListener(v -> this.switchToCategory(Category.MECHANICAL));

        this.bottomNavigationViewModel = new ViewModelProvider(this).get(BottomNavigationViewModel.class);
        this.bottomNavigationViewModel.setSelectedItemId(R.id.navigation_home);

        this.getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.red_top_bar));
        this.getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.background_light_gray));
    }

    private void switchToCategory(final Category category) {
        final Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra(Constants.IntentKeys.CATEGORY_ID, category.getId());
        this.startActivity(intent);
    }
}