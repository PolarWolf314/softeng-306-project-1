package nz.ac.aucklanduni.se306project1.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import nz.ac.aucklanduni.se306project1.R;
import nz.ac.aucklanduni.se306project1.adapters.ListRecyclerAdapter;
import nz.ac.aucklanduni.se306project1.builders.ui.CategoryFilterBuilder;
import nz.ac.aucklanduni.se306project1.data.Constants;
import nz.ac.aucklanduni.se306project1.databinding.ActivityListBinding;
import nz.ac.aucklanduni.se306project1.iconbuttons.BackButton;
import nz.ac.aucklanduni.se306project1.itemdecorations.GridSpacingItemDecoration;
import nz.ac.aucklanduni.se306project1.models.enums.Category;
import nz.ac.aucklanduni.se306project1.models.items.Item;
import nz.ac.aucklanduni.se306project1.viewholders.ItemCardViewHolder;
import nz.ac.aucklanduni.se306project1.viewmodels.ItemSearchViewModel;
import nz.ac.aucklanduni.se306project1.viewmodels.ListViewModel;

public class ListActivity extends TopBarActivity {

    private ActivityListBinding binding;
    private ItemSearchViewModel searchViewModel;
    private ListViewModel listViewModel;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = ActivityListBinding.inflate(this.getLayoutInflater());
        this.setContentView(this.binding.getRoot());

        final Category category = Category.fromId(this.getIntent().getStringExtra(Constants.IntentKeys.CATEGORY_ID));

        this.listViewModel = new ViewModelProvider(this, ViewModelProvider.Factory.from(ListViewModel.initializer))
                .get(ListViewModel.class);
        this.searchViewModel = new ViewModelProvider(this).get(ItemSearchViewModel.class);

        this.listViewModel.getItemDataProvider().getItemsByCategory(category)
                .thenAccept(this.searchViewModel::setOriginalItems);

        final RecyclerView recyclerView = this.binding.listRecyclerView;

        final ListRecyclerAdapter<Item, ?> adapter = new ListRecyclerAdapter<>(
                this, this.searchViewModel.getFilteredItems(), ItemCardViewHolder.Builder.INSTANCE);

        recyclerView.setAdapter(adapter);
        GridSpacingItemDecoration.attachGrid(recyclerView, this, 2, 12, 20);

        this.topBarViewModel.setStartIconButton(new BackButton(new Intent(this, HomeActivity.class)));
        this.topBarViewModel.setTitle(category.getDisplayName(this.getResources()));
        this.binding.categoryImage.setImageResource(category.getCategoryImageId());

        this.addCategoryFilterView(category);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Set the layout to extend into the system gesture area
            final Window window = this.getWindow();
            window.setDecorFitsSystemWindows(false);

            // Calculate the height of the system NavigationBar
            final int navBarHeight = this.getSystemNavigationBarHeight();

            // Get a reference to your custom bottom navigation bar view
            final View customBottomNavBar = this.binding.bottomNavigationFragmentContainerView;

            // Add margin to the custom bottom navigation bar
            final ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) customBottomNavBar.getLayoutParams();
            layoutParams.bottomMargin = navBarHeight;
            customBottomNavBar.setLayoutParams(layoutParams);
        }

        this.getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.transparent));
        this.getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.background_light_gray));
    }

    private void addCategoryFilterView(final Category category) {
        final CategoryFilterBuilder builder = category.getCategoryFilterBuilder();
        if (builder == null) return;

        final View categoryFilterView = builder.buildFilteringView(
                this, this.getLayoutInflater(), this.searchViewModel);

        this.binding.topBarContainer.addView(categoryFilterView);
    }

    private int getSystemNavigationBarHeight() {
        final int resourceId = this.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return this.getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }
}