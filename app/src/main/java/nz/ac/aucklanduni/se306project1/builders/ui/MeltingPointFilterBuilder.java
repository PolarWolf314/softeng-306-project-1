package nz.ac.aucklanduni.se306project1.builders.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.slider.RangeSlider;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import nz.ac.aucklanduni.se306project1.data.Constants;
import nz.ac.aucklanduni.se306project1.databinding.ChemmatSliderFilterBinding;
import nz.ac.aucklanduni.se306project1.models.items.ChemmatItem;
import nz.ac.aucklanduni.se306project1.viewmodels.ItemSearchViewModel;

public class MeltingPointFilterBuilder implements CategoryFilterBuilder {

    @Override
    public View buildFilteringView(
            final Context context,
            final LayoutInflater inflater,
            final ItemSearchViewModel searchViewModel
    ) {
        final ChemmatSliderFilterBinding binding = ChemmatSliderFilterBinding.inflate(inflater);
        final RangeSlider slider = binding.slider;

        this.setMinAndMaxValues(slider, searchViewModel);
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

    private void setMinAndMaxValues(final RangeSlider slider, final ItemSearchViewModel searchViewModel) {
        final List<ChemmatItem> items = searchViewModel.getOriginalItems()
                .stream()
                .filter(item -> item instanceof ChemmatItem)
                .map(item -> (ChemmatItem) item)
                .collect(Collectors.toList());

        float minValue = 0, maxValue = 0;
        if (items.size() > 1) {
            final ChemmatItem firstItem = items.get(0);
            maxValue = minValue = firstItem.getMeltingPoint();
        }

        for (final ChemmatItem item : items) {
            if (item.getMeltingPoint() < minValue) {
                minValue = item.getMeltingPoint();
            } else if (item.getMeltingPoint() > maxValue) {
                maxValue = item.getMeltingPoint();
            }
        }

        slider.setValueFrom(minValue);
        slider.setValueTo(maxValue);
        slider.setValues(minValue, maxValue);
    }

    private String formatValue(final float value) {
        return String.format(Locale.getDefault(), "%.1f°C", value);
    }

    private void applyFilters(final RangeSlider slider, final ItemSearchViewModel searchViewModel) {
        final List<Float> values = slider.getValues();
        final float minValue = values.get(0);
        final float maxValue = values.get(1);

        if (minValue == slider.getValueFrom() && maxValue == slider.getValueTo()) {
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
