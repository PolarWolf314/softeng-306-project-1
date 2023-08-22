package nz.ac.aucklanduni.se306project1.activities;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.RadioGroup;

import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.radiobutton.MaterialRadioButton;

import java.util.List;

import nz.ac.aucklanduni.se306project1.R;
import nz.ac.aucklanduni.se306project1.databinding.ActivityDetailsBinding;
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

        final String itemId = "bEaYWrsVPWxwFMM8s8wp";

        this.detailsViewModel = new ViewModelProvider(this, ViewModelProvider.Factory.from(DetailsViewModel.INITIALIZER))
                .get(DetailsViewModel.class);

        this.detailsViewModel.getItemDataProvider().getItemById(itemId)
                .thenAccept(this::bindItemData);
    }

    private void bindItemData(final Item item) {
        this.topBarViewModel.setTitle(item.getDisplayName());
        System.out.println("Loaded item " + item.getDisplayName());

        this.generateColourOptions(item);
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
        }

        radioGroup.setOnCheckedChangeListener((group, checkedItemPosition) -> {
            final int checkedItemIndex = checkedItemPosition - 1;
            System.out.println("Selected: " + checkedItemIndex);
        });
    }
}