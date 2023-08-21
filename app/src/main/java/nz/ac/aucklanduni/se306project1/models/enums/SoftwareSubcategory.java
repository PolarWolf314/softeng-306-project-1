package nz.ac.aucklanduni.se306project1.models.enums;

import androidx.annotation.StringRes;

import nz.ac.aucklanduni.se306project1.R;
import nz.ac.aucklanduni.se306project1.models.Subcategory;

public enum SoftwareSubcategory implements Subcategory {
    LINUX_CHAD(R.string.software_subcategory_linux_chad),
    APPLE_FANBOY(R.string.software_subcategory_apple_fanboy),
    DEFAULT_SKIN(R.string.software_subcategory_default_skin);

    private final int displayNameId;

    SoftwareSubcategory(@StringRes final int displayNameId) {
        this.displayNameId = displayNameId;
    }

    @Override
    public int getDisplayNameId() {
        return this.displayNameId;
    }
}
