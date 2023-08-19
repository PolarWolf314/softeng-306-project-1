package nz.ac.aucklanduni.se306project1.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import nz.ac.aucklanduni.se306project1.adapters.ListRecyclerAdapter;
import nz.ac.aucklanduni.se306project1.data.MockData;
import nz.ac.aucklanduni.se306project1.databinding.ActivityListBinding;
import nz.ac.aucklanduni.se306project1.iconbuttons.BackButton;
import nz.ac.aucklanduni.se306project1.itemdecorations.GridSpacingItemDecoration;
import nz.ac.aucklanduni.se306project1.models.items.Item;
import nz.ac.aucklanduni.se306project1.viewholders.ItemCardViewHolder;
import nz.ac.aucklanduni.se306project1.viewmodels.ItemSearchViewModel;

public class ListActivity extends TopBarActivity {

    private ActivityListBinding binding;
    private ItemSearchViewModel searchViewModel;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = ActivityListBinding.inflate(this.getLayoutInflater());
        this.setContentView(this.binding.getRoot());

        // Just so that we have to scroll, add the mock data twice
        final List<Item> items = new ArrayList<>();
        items.addAll(MockData.ITEMS);
        items.addAll(MockData.ITEMS);

        this.searchViewModel = new ViewModelProvider(this).get(ItemSearchViewModel.class);
        this.searchViewModel.setOriginalItems(items);
        final RecyclerView recyclerView = this.binding.listRecyclerView;

        final ListRecyclerAdapter<Item, ?> adapter = new ListRecyclerAdapter<>(
                this, this.searchViewModel.getFilteredItems(), ItemCardViewHolder.Builder.INSTANCE);

        recyclerView.setAdapter(adapter);
        GridSpacingItemDecoration.attachGrid(recyclerView, this, 2, 12, 20);

        this.topBarViewModel.setStartIconButton(new BackButton(new Intent(this, HomeActivity.class)));
        this.topBarViewModel.setTitle("Civil");
    }
}