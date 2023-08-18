package nz.ac.aucklanduni.se306project1.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import nz.ac.aucklanduni.se306project1.adapters.ListRecyclerAdapter;
import nz.ac.aucklanduni.se306project1.data.MockData;
import nz.ac.aucklanduni.se306project1.databinding.ActivityWatchlistBinding;
import nz.ac.aucklanduni.se306project1.itemdecorations.GridSpacingItemDecoration;
import nz.ac.aucklanduni.se306project1.models.items.Item;
import nz.ac.aucklanduni.se306project1.viewholders.ItemCardViewHolder;
import nz.ac.aucklanduni.se306project1.viewmodels.ItemSearchViewModel;

public class WatchlistActivity extends AppCompatActivity {

    private ActivityWatchlistBinding binding;
    private ItemSearchViewModel searchViewModel;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = ActivityWatchlistBinding.inflate(this.getLayoutInflater());
        this.setContentView(this.binding.getRoot());

        this.searchViewModel = new ViewModelProvider(this).get(ItemSearchViewModel.class);
        this.searchViewModel.setOriginalItems(MockData.ITEMS);
        final RecyclerView recyclerView = this.binding.watchlistRecyclerView;

        final ListRecyclerAdapter<Item, ?> adapter = new ListRecyclerAdapter<>(
                this, this.searchViewModel.getFilteredItems(), ItemCardViewHolder.Builder.INSTANCE);

        recyclerView.setAdapter(adapter);
        GridSpacingItemDecoration.attachGrid(recyclerView, this, 2, 12, 20);
    }
}