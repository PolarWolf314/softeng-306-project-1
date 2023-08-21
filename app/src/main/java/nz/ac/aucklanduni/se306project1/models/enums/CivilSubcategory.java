package nz.ac.aucklanduni.se306project1.models.enums;

import androidx.annotation.StringRes;

import nz.ac.aucklanduni.se306project1.R;
import nz.ac.aucklanduni.se306project1.models.Subcategory;

public enum CivilSubcategory implements Subcategory {
    PPE(R.string.civil_subcategory_ppe),
    CLOTHING(R.string.civil_subcategory_clothing),
    VEHICLES(R.string.civil_subcategory_vehicles),
    TOOLS(R.string.civil_subcategory_tools);

    private final int displayNameId;

    CivilSubcategory(@StringRes final int displayNameId) {
        this.displayNameId = displayNameId;
    }

    @Override
    public int getDisplayNameId() {
        return this.displayNameId;
    }
}
