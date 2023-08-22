package nz.ac.aucklanduni.se306project1.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

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

        this.listViewModel = new ViewModelProvider(this, ViewModelProvider.Factory.from(ListViewModel.INITIALIZER))
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
        this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        this.topBarViewModel.setTitle(category.getDisplayName(this.getResources()));
        this.binding.categoryImage.setImageResource(category.getCategoryImageId());

        this.addCategoryFilterView(category);

        this.getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.transparent));
        this.getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.background_light_gray));

        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        this.getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

    private void addCategoryFilterView(final Category category) {
        final CategoryFilterBuilder builder = category.getCategoryFilterBuilder();
        if (builder == null) return;

        final View categoryFilterView = builder.buildFilteringView(
                this, this.getLayoutInflater(), this.searchViewModel);

        this.binding.topBarContainer.addView(categoryFilterView);
    }

}