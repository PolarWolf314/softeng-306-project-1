package nz.ac.aucklanduni.se306project1.ui;

import android.view.View;

import nz.ac.aucklanduni.se306project1.R;

public class LoadingSpinner {
    private final View spinnerContainer;

    public LoadingSpinner(final View root) {
        this.spinnerContainer = root.findViewById(R.id.spinner);
    }

    public void show() {
        this.spinnerContainer.setVisibility(View.VISIBLE);
    }

    public void hide() {
        this.spinnerContainer.setVisibility(View.GONE);
    }
}
