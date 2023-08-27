package nz.ac.aucklanduni.se306project1.ui;

import android.view.View;

public class LoadingSpinner {
    private final View spinnerContainer;

    public LoadingSpinner(final View spinnerContainer) {
        this.spinnerContainer = spinnerContainer;
    }

    public void show() {
        this.spinnerContainer.setVisibility(View.VISIBLE);
    }

    public void hide() {
        this.spinnerContainer.setVisibility(View.GONE);
    }
}
