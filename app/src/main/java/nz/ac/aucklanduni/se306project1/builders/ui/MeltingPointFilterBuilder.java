package nz.ac.aucklanduni.se306project1.builders.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.slider.RangeSlider;

import java.util.List;
import java.util.Locale;

import nz.ac.aucklanduni.se306project1.data.Constants;
import nz.ac.aucklanduni.se306project1.databinding.ChemmatSliderFilterBinding;
import nz.ac.aucklanduni.se306project1.models.items.ChemmatItem;
import nz.ac.aucklanduni.se306project1.viewmodels.ItemSearchViewModel;

public class MeltingPointFilterBuilder implements CategoryFilterBuilder {

    private final float minValue;
    private final float maxValue;

    public MeltingPointFilterBuilder(final float minValue, final float maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public View buildFilteringView(
            final Context context,
            final LayoutInflater inflater,
            final ItemSearchViewModel searchViewModel
    ) {
        final ChemmatSliderFilterBinding binding = ChemmatSliderFilterBinding.inflate(inflater);
        final RangeSlider slider = binding.slider;

        slider.setValueFrom(this.minValue);
        slider.setValueTo(this.maxValue);

        slider.setValues(this.minValue, this.maxValue);
        slider.setLabelFormatter(this::formatValue);

        slider.addOnSliderTouchListener(new RangeSlider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull final RangeSlider slider) {
            }

            @Override
            public void onStopTrackingTouch(@NonNull final RangeSlider slider) {
                MeltingPointFilterBuilder.this.applyFilters(slider, searchViewModel);
            }
        });

        return binding.getRoot();
    }

    private String formatValue(final float value) {
        return String.format(Locale.getDefault(), "%.1f°C", value);
    }

    private void applyFilters(final RangeSlider slider, final ItemSearchViewModel searchViewModel) {
        final List<Float> values = slider.getValues();
        final float minValue = values.get(0);
        final float maxValue = values.get(1);

        if (minValue == this.minValue && maxValue == this.maxValue) {
            searchViewModel.removeFilter(Constants.FilterKeys.SLIDER_FILTERING);
        } else {
            searchViewModel.putFilter(Constants.FilterKeys.SLIDER_FILTERING,
                    (item) -> {
                        if (!(item instanceof ChemmatItem)) return false;
                        final ChemmatItem chemmatItem = (ChemmatItem) item;
                        return chemmatItem.getMeltingPoint() >= minValue && chemmatItem.getMeltingPoint() <= maxValue;
                    });
        }
    }
}
