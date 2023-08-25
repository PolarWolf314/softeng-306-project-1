package nz.ac.aucklanduni.se306project1.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import nz.ac.aucklanduni.se306project1.R;
import nz.ac.aucklanduni.se306project1.adapters.ListRecyclerAdapter;
import nz.ac.aucklanduni.se306project1.databinding.ActivityWatchlistBinding;
import nz.ac.aucklanduni.se306project1.itemdecorations.GridSpacingItemDecoration;
import nz.ac.aucklanduni.se306project1.models.items.Item;
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
        this.watchlistViewModel = new ViewModelProvider(this, ViewModelProvider.Factory.from(WatchlistViewModel.initializer)).get(WatchlistViewModel.class);
        this.watchlistViewModel.getWatchlistItems()
                .observe(this, items -> this.searchViewModel.setOriginalItems(new ArrayList<>(items)));

        final RecyclerView recyclerView = this.binding.watchlistRecyclerView;

        final ListRecyclerAdapter<Item, ?> adapter = new ListRecyclerAdapter<>(
                this.getApplication(), this.searchViewModel.getFilteredItems(), new ItemCardViewHolder.Builder(this.watchlistViewModel));

        recyclerView.setAdapter(adapter);
        GridSpacingItemDecoration.attachGrid(recyclerView, this, 2, 12, 20);
        this.bottomNavigationViewModel = new ViewModelProvider(this).get(BottomNavigationViewModel.class);
        this.bottomNavigationViewModel.setSelectedItemId(R.id.navigation_watchlist);

        this.getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.red_top_bar));
        this.getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.background_light_gray));
    }
}