package nz.ac.aucklanduni.se306project1.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentContainerView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nz.ac.aucklanduni.se306project1.R;
import nz.ac.aucklanduni.se306project1.adapters.ListRecyclerAdapter;
import nz.ac.aucklanduni.se306project1.data.Constants;
import nz.ac.aucklanduni.se306project1.databinding.ActivityListBinding;
import nz.ac.aucklanduni.se306project1.iconbuttons.BackButton;
import nz.ac.aucklanduni.se306project1.itemdecorations.GridSpacingItemDecoration;
import nz.ac.aucklanduni.se306project1.models.enums.Category;
import nz.ac.aucklanduni.se306project1.models.items.Item;
import nz.ac.aucklanduni.se306project1.ui.CategoryFilterBuilder;
import nz.ac.aucklanduni.se306project1.utils.StringUtils;
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

        this.listViewModel = new ViewModelProvider(this, ViewModelProvider.Factory.from(ListViewModel.INITIALIZER))
                .get(ListViewModel.class);
        this.searchViewModel = new ViewModelProvider(this).get(ItemSearchViewModel.class);

        this.topBarViewModel.setStartIconButton(new BackButton());
        this.topBarViewModel.getIsSearchBarExpanded().observe(this, this::onChangeSearchBarExpansion);

        this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        this.setCategoryInformation();
        this.setSystemBarColours();
    }

    private void updateItemCountLabel(final List<Item> items) {
        final String label = StringUtils.getQuantity(this.getResources(), R.plurals.number_of_items, R.string.no_items, items.size());
        this.binding.categoryItemCount.setText(label);
    }

    private void onChangeSearchBarExpansion(final boolean isExpanded) {
        if (isExpanded) {
            this.binding.appBar.setExpanded(false);
        }
    }

    private void setSystemBarColours() {
        this.getWindow().setStatusBarColor(Color.TRANSPARENT);
        this.getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.background_light_gray));

        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        int statusBarHeight = 0;
        final int heightResourceId = this.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (heightResourceId > 0) {
            statusBarHeight = this.getResources().getDimensionPixelSize(heightResourceId);
        }

        final FragmentContainerView topBarFragmentContainer = this.binding.topBarFragmentContainer;
        topBarFragmentContainer.setPadding(
                topBarFragmentContainer.getPaddingLeft(),
                topBarFragmentContainer.getPaddingTop() + statusBarHeight,
                topBarFragmentContainer.getPaddingRight(),
                topBarFragmentContainer.getPaddingBottom());
    }

    private void setCategoryInformation() {
        final Category category = Category.fromId(this.getIntent().getStringExtra(Constants.IntentKeys.CATEGORY_ID));

        this.topBarViewModel.setTitle(category.getDisplayName(this.getResources()));
        this.binding.categoryImage.setImageResource(category.getCategoryImageId());

        this.addCategoryFilterView(category);
        this.displayItems(category);
    }

    private void displayItems(final Category category) {
        if (category == Category.ALL_ITEMS) {
            this.topBarViewModel.setSearchBarExpanded(true);
            this.listViewModel.getAllItems().thenAccept(this.searchViewModel::setOriginalItems);
        } else {
            this.listViewModel.getItemsByCategory(category).thenAccept(this.searchViewModel::setOriginalItems);
        }

        final RecyclerView recyclerView = this.binding.listRecyclerView;
        final ListRecyclerAdapter<Item, ?> adapter = new ListRecyclerAdapter<>(
                this, this.searchViewModel.getFilteredItems(), new ItemCardViewHolder.Builder(this.listViewModel));

        recyclerView.setAdapter(adapter);
        GridSpacingItemDecoration.attachGrid(recyclerView, this, 2, 12, 20);
        this.searchViewModel.getFilteredItems().observe(this, this::updateItemCountLabel);
    }

    private void addCategoryFilterView(final Category category) {
        final CategoryFilterBuilder builder = category.getCategoryFilterBuilder();
        if (builder == null) return;

        final View categoryFilterView = builder.buildFilteringView(
                this, this.getLayoutInflater(), this.searchViewModel);

        this.binding.topBarContainer.addView(categoryFilterView);
    }

}