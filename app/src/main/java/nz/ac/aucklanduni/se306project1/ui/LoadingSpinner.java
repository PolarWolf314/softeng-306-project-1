package nz.ac.aucklanduni.se306project1.ui;

import android.content.res.ColorStateList;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.ColorInt;

import nz.ac.aucklanduni.se306project1.R;

public class LoadingSpinner {
    private final ViewGroup spinnerContainer;

    public LoadingSpinner(final View root) {
        this.spinnerContainer = root.findViewById(R.id.spinner);
    }

    public void setColor(@ColorInt final int colourId) {
        final ProgressBar spinner = (ProgressBar) this.spinnerContainer.getChildAt(0);
        spinner.setIndeterminateTintList(ColorStateList.valueOf(colourId));
    }

    public void show() {
        this.spinnerContainer.setVisibility(View.VISIBLE);
    }

    public void hide() {
        this.spinnerContainer.setVisibility(View.GONE);
    }
}
