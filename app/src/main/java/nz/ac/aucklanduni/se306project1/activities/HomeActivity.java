package nz.ac.aucklanduni.se306project1.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import nz.ac.aucklanduni.se306project1.R;
import nz.ac.aucklanduni.se306project1.adapters.ListRecyclerAdapter;
import nz.ac.aucklanduni.se306project1.data.MockData;
import nz.ac.aucklanduni.se306project1.databinding.ActivityHomeBinding;
import nz.ac.aucklanduni.se306project1.itemdecorations.ItemSpacingDecoration;
import nz.ac.aucklanduni.se306project1.models.items.Item;
import nz.ac.aucklanduni.se306project1.viewholders.FeaturedItemCardViewHolder;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = ActivityHomeBinding.inflate(this.getLayoutInflater());
        this.setContentView(this.binding.getRoot());

        final RecyclerView recyclerView = this.binding.featuredProductsRecyclerView;
        final ListRecyclerAdapter<Item, ?> adapter = new ListRecyclerAdapter<>(
                this, MockData.ITEMS, FeaturedItemCardViewHolder.Builder.INSTANCE);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        final int horizontalSpacingInPixels = this.getResources().getDimensionPixelSize(R.dimen.horizontal_item_spacing);
        recyclerView.addItemDecoration(new ItemSpacingDecoration(this, horizontalSpacingInPixels));

        final Intent intent = new Intent(this, ListActivity.class);

        final CardView civilCategoryCard = this.findViewById(R.id.civil_category);
        civilCategoryCard.setOnClickListener(v -> {
            // Create intent and navigate to ListActivity
            this.startActivity(intent);
        });

        final CardView softwareCategoryCard = this.findViewById(R.id.software_category);
        softwareCategoryCard.setOnClickListener(v -> {
            // Create intent and navigate to ListActivity
            this.startActivity(intent);
        });

        final CardView chemmatCategoryCard = this.findViewById(R.id.chemmat_category);
        chemmatCategoryCard.setOnClickListener(v -> {
            // Create intent and navigate to ListActivity
            this.startActivity(intent);
        });

        final CardView mechanicalCategoryCard = this.findViewById(R.id.mechanical_category);
        mechanicalCategoryCard.setOnClickListener(v -> {
            // Create intent and navigate to ListActivity
            this.startActivity(intent);
        });

    }
}