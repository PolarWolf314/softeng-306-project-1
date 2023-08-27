package nz.ac.aucklanduni.se306project1.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import nz.ac.aucklanduni.se306project1.R;
import nz.ac.aucklanduni.se306project1.adapters.ListRecyclerAdapter;
import nz.ac.aucklanduni.se306project1.data.Constants;
import nz.ac.aucklanduni.se306project1.databinding.ActivityHomeBinding;
import nz.ac.aucklanduni.se306project1.iconbuttons.HomeSearchIcon;
import nz.ac.aucklanduni.se306project1.itemdecorations.HorizontalItemSpacingDecoration;
import nz.ac.aucklanduni.se306project1.models.enums.Category;
import nz.ac.aucklanduni.se306project1.models.items.Item;
import nz.ac.aucklanduni.se306project1.ui.LoadingSpinner;
import nz.ac.aucklanduni.se306project1.utils.StringUtils;
import nz.ac.aucklanduni.se306project1.viewholders.FeaturedItemCardViewHolderBuilder;
import nz.ac.aucklanduni.se306project1.viewmodels.BottomNavigationViewModel;
import nz.ac.aucklanduni.se306project1.viewmodels.HomeViewModel;

public class HomeActivity extends TopBarActivity {

    private ActivityHomeBinding binding;
    private HomeViewModel homeViewModel;
    private BottomNavigationViewModel bottomNavigationViewModel;
    private LoadingSpinner loadingSpinner;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = ActivityHomeBinding.inflate(this.getLayoutInflater());
        this.setContentView(this.binding.getRoot());

        this.homeViewModel = new ViewModelProvider(this, ViewModelProvider.Factory.from(HomeViewModel.INITIALIZER)).get(HomeViewModel.class);
        this.bottomNavigationViewModel = new ViewModelProvider(this).get(BottomNavigationViewModel.class);

        this.bottomNavigationViewModel.setSelectedItemId(R.id.navigation_home);
        this.topBarViewModel.setEndIconButton(new HomeSearchIcon());

        this.loadingSpinner = new LoadingSpinner(this.binding.getRoot());

        this.displayFeaturedItems();
        this.setupCategories();

        this.getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.red_top_bar));
        this.getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.background_light_gray));
    }

    private void displayFeaturedItems() {

        final MutableLiveData<List<Item>> featuredItems = new MutableLiveData<>(Collections.emptyList());

        this.loadingSpinner.show();
        this.homeViewModel.getFeaturedItems().thenAccept(items -> {
            this.loadingSpinner.hide();
            featuredItems.setValue(items);
        });

        final RecyclerView recyclerView = this.binding.featuredProductsRecyclerView;
        final ListRecyclerAdapter<Item, ?> adapter = new ListRecyclerAdapter<>(
                this, featuredItems, new FeaturedItemCardViewHolderBuilder(this.homeViewModel));

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        final int horizontalSpacingInPixels = this.getResources().getDimensionPixelSize(R.dimen.horizontal_item_spacing);
        recyclerView.addItemDecoration(new HorizontalItemSpacingDecoration(this, horizontalSpacingInPixels));

    }

    private void switchToCategory(final Category category) {
        final Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra(Constants.IntentKeys.CATEGORY_ID, category.getId());
        this.startActivity(intent);
    }

    private void setupCategories() {
        this.setupCategory(this.binding.civilCategory, this.binding.civilItemCount, Category.CIVIL);
        this.setupCategory(this.binding.softwareCategory, this.binding.softwareItemCount, Category.SOFTWARE);
        this.setupCategory(this.binding.chemmatCategory, this.binding.chemmatItemCount, Category.CHEMMAT);
        this.setupCategory(this.binding.mechanicalCategory, this.binding.mechanicalItemCount, Category.MECHANICAL);
    }

    private void setupCategory(
            final CardView card,
            final TextView labelView,
            final Category category
    ) {
        card.setOnClickListener(v -> this.switchToCategory(category));
        this.homeViewModel.getItemCountPerCategory(category).thenAccept(count -> {
            final String label = StringUtils.getQuantity(this.getResources(), R.plurals.number_of_items, R.string.no_items, count);
            labelView.setText(label);
        });
    }
}