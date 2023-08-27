package nz.ac.aucklanduni.se306project1.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Set;

import nz.ac.aucklanduni.se306project1.R;
import nz.ac.aucklanduni.se306project1.adapters.ListRecyclerAdapter;
import nz.ac.aucklanduni.se306project1.databinding.ActivityWatchlistBinding;
import nz.ac.aucklanduni.se306project1.itemdecorations.GridSpacingItemDecoration;
import nz.ac.aucklanduni.se306project1.models.items.Item;
import nz.ac.aucklanduni.se306project1.ui.LoadingSpinner;
import nz.ac.aucklanduni.se306project1.utils.StringUtils;
import nz.ac.aucklanduni.se306project1.viewholders.ItemCardViewHolder;
import nz.ac.aucklanduni.se306project1.viewmodels.BottomNavigationViewModel;
import nz.ac.aucklanduni.se306project1.viewmodels.ItemSearchViewModel;
import nz.ac.aucklanduni.se306project1.viewmodels.WatchlistViewModel;

public class WatchlistActivity extends AppCompatActivity {

    private ActivityWatchlistBinding binding;
    private ItemSearchViewModel searchViewModel;

    private WatchlistViewModel watchlistViewModel;
    private BottomNavigationViewModel bottomNavigationViewModel;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = ActivityWatchlistBinding.inflate(this.getLayoutInflater());
        this.setContentView(this.binding.getRoot());


        this.searchViewModel = new ViewModelProvider(this).get(ItemSearchViewModel.class);
        this.bottomNavigationViewModel = new ViewModelProvider(this).get(BottomNavigationViewModel.class);
        this.watchlistViewModel = new ViewModelProvider(this, ViewModelProvider.Factory.from(WatchlistViewModel.initializer)).get(WatchlistViewModel.class);

        this.bottomNavigationViewModel.setSelectedItemId(R.id.navigation_watchlist);
        this.watchlistViewModel.getWatchlistItems().observe(this, this::onWatchlistItemsLoaded);
        this.watchlistViewModel.setSpinner(new LoadingSpinner(this.binding.getRoot()));

        this.binding.clearWatchlist.setOnClickListener(v -> this.watchlistViewModel.clearWatchlist());


        this.displayItems();

        this.getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.red_top_bar));
        this.getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.background_light_gray));
    }

    private void displayItems() {
        final RecyclerView recyclerView = this.binding.watchlistRecyclerView;
        final ListRecyclerAdapter<Item, ?> adapter = new ListRecyclerAdapter<>(
                this, this.searchViewModel.getFilteredItems(), new ItemCardViewHolder.Builder(this.watchlistViewModel));

        recyclerView.setAdapter(adapter);
        GridSpacingItemDecoration.attachGrid(recyclerView, this, 2, 12, 20);
    }

    private void onWatchlistItemsLoaded(final Set<Item> watchlistItems) {
        this.searchViewModel.setOriginalItems(new ArrayList<>(watchlistItems));
        final String label = StringUtils.getQuantity(
                this.getResources(), R.plurals.number_of_items_in_watchlist,
                R.string.no_items_in_watchlist, watchlistItems.size());
        this.binding.itemsInWatchlist.setText(label);
    }
}