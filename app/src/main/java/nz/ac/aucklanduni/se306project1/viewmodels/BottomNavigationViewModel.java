package nz.ac.aucklanduni.se306project1.viewmodels;

import androidx.lifecycle.ViewModel;

public class BottomNavigationViewModel extends ViewModel {

    private int selectedItemId;

    public int getSelectedItemId() {
        return this.selectedItemId;
    }

    public void setSelectedItemId(final int selectedItemId) {
        this.selectedItemId = selectedItemId;
    }
}
