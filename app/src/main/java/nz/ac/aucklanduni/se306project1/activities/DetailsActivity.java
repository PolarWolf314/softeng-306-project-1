package nz.ac.aucklanduni.se306project1.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.radiobutton.MaterialRadioButton;

import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import nz.ac.aucklanduni.se306project1.R;
import nz.ac.aucklanduni.se306project1.data.Constants;
import nz.ac.aucklanduni.se306project1.databinding.ActivityDetailsBinding;
import nz.ac.aucklanduni.se306project1.databinding.ItemSizeRadioBinding;
import nz.ac.aucklanduni.se306project1.iconbuttons.BackButton;
import nz.ac.aucklanduni.se306project1.iconbuttons.WatchlistButton;
import nz.ac.aucklanduni.se306project1.models.items.ColouredItemInformation;
import nz.ac.aucklanduni.se306project1.models.items.Item;
import nz.ac.aucklanduni.se306project1.viewmodels.DetailsViewModel;

public class DetailsActivity extends TopBarActivity {

    private ActivityDetailsBinding binding;
    private DetailsViewModel detailsViewModel;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = ActivityDetailsBinding.inflate(this.getLayoutInflater());
        this.setContentView(this.binding.getRoot());

        final String itemId = this.getIntent().getStringExtra(Constants.IntentKeys.ITEM_ID);

        this.binding.detailsCarousel.registerLifecycle(this.getLifecycle());
        this.detailsViewModel = new ViewModelProvider(this, ViewModelProvider.Factory.from(DetailsViewModel.INITIALIZER))
                .get(DetailsViewModel.class);

        this.detailsViewModel.getItemDataProvider().getItemById(itemId)
                .thenAccept(this::bindItemData);

        this.detailsViewModel.getSelectedColourInfo().observe(this, this::setColourInformation);

        final Intent intent = new Intent(this, HomeActivity.class);

        this.topBarViewModel.setStartIconButton(new BackButton(intent));
        this.topBarViewModel.setEndIconButton(new WatchlistButton(false, this.detailsViewModel::toggleIsInWatchlist));

        this.detailsViewModel.isInWatchlist().observe(this, isInWatchlist ->
                this.topBarViewModel.setEndIconButton(new WatchlistButton(isInWatchlist, this.detailsViewModel::toggleIsInWatchlist)));
    }

    private void bindItemData(final Item item) {
        this.topBarViewModel.setTitle(item.getDisplayName());

        this.generateColourOptions(item);
        this.binding.addToCartButton.setText(this.getResources().getString(R.string.add_to_cart, item.getPrice()));

        if (item.getColours().size() >= 1) {
            this.setColourInformation(item.getColours().get(0));
        }

        this.binding.detailsItemDescription.setText(item.getDescription());
    }

    @SuppressLint("RestrictedApi")
    private void generateColourOptions(final Item item) {
        final RadioGroup radioGroup = this.binding.detailsItemColorSelectorRadioGroup;
        final List<ColouredItemInformation> colouredItemInfo = item.getColours();

        for (int index = 0; index < colouredItemInfo.size(); index++) {
            final ColouredItemInformation colouredInfo = colouredItemInfo.get(index);

            final MaterialRadioButton radio = new MaterialRadioButton(this);
            radio.setButtonDrawable(R.drawable.colour_option_selector);
            radio.setSupportButtonTintList(ColorStateList.valueOf(Color.parseColor(colouredInfo.getColour())));
            radioGroup.addView(radio);

            if (index == 0) {
                // Note: We can only check it AFTER adding it to the radio group
                radio.setChecked(true);
            }

            radio.setOnCheckedChangeListener((r, isChecked) -> {
                if (isChecked) this.detailsViewModel.setSelectedColourInfo(colouredInfo);
            });
        }
    }

    private void setColourInformation(final ColouredItemInformation colourInfo) {
        final int parsedColour = Color.parseColor(colourInfo.getColour());

        this.getWindow().setStatusBarColor(parsedColour);
        this.getWindow().setNavigationBarColor(parsedColour);

        this.generateSizeOptions(colourInfo.getSizeQuantities());

        this.binding.detailsCarousel.setData(
                colourInfo.getImages()
                        .stream()
                        .map(imageInfo -> new CarouselItem(imageInfo.getUrl()))
                        .collect(Collectors.toList())
        );
        this.binding.detailsLayout.setBackgroundColor(parsedColour);
    }

    private void generateSizeOptions(final Map<String, Integer> sizeQuantities) {
        final List<String> sizes = sizeQuantities.keySet()
                .stream()
                .filter(size -> sizeQuantities.get(size) > 0)
                .collect(Collectors.toList());

        if (this.detailsViewModel.getCurrentSizes().equals(sizes)) {
            // The sizes are the same, we don't need to regenerate
            return;
        }

        this.detailsViewModel.setCurrentSizes(sizes);
        final RadioGroup radioGroup = this.binding.sizeSelector;
        radioGroup.removeAllViews();

        for (int index = 0; index < sizes.size(); index++) {
            final String size = sizes.get(index);
            final RadioButton radio = ItemSizeRadioBinding.inflate(this.getLayoutInflater()).getRoot();
            radio.setText(size.toUpperCase());
            radioGroup.addView(radio);

            if (index == 0) {
                radio.setChecked(true);
            }

            radio.setOnCheckedChangeListener((r, isChecked) -> {
                if (isChecked) this.detailsViewModel.setSelectedSize(size);
            });
        }
    }
}