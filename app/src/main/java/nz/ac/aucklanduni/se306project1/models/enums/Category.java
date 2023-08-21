package nz.ac.aucklanduni.se306project1.models.enums;

import android.content.res.Resources;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

import java.util.HashMap;
import java.util.Map;

import nz.ac.aucklanduni.se306project1.R;
import nz.ac.aucklanduni.se306project1.models.items.ChemmatItem;
import nz.ac.aucklanduni.se306project1.models.items.CivilItem;
import nz.ac.aucklanduni.se306project1.models.items.Item;
import nz.ac.aucklanduni.se306project1.models.items.MechanicalItem;
import nz.ac.aucklanduni.se306project1.models.items.SoftwareItem;

public enum Category {
    CIVIL("civil", CivilItem.class, R.string.civil, R.drawable.civil_category_image),
    SOFTWARE("software", SoftwareItem.class, R.string.software, R.drawable.software_category_image),
    CHEMMAT("chemmat", ChemmatItem.class, R.string.chemmat, R.drawable.chemmat_category_image),
    MECHANICAL("mechanical", MechanicalItem.class, R.string.mechanical, R.drawable.mechanical_category_image);

    public static final Map<String, Category> mappedCategories = new HashMap<>();

    static {
        for (final Category category : values()) {
            mappedCategories.put(category.getId(), category);
        }
    }

    private final String id;
    private final Class<? extends Item> itemClass;
    @StringRes
    private final int displayNameId;
    @DrawableRes
    private final int categoryImageId;

    Category(
            final String id,
            final Class<? extends Item> itemClass,
            @StringRes final int displayNameId,
            @DrawableRes final int categoryImageId
    ) {
        this.id = id;
        this.itemClass = itemClass;
        this.displayNameId = displayNameId;
        this.categoryImageId = categoryImageId;
    }

    /**
     * Retrieves the {@link Category} instance associated with the specified id.
     *
     * @param categoryId The id of the category to retrieve
     * @return THe {@link Category} or null if there is none with the id
     */
    public static Category fromId(final String categoryId) {
        return mappedCategories.get(categoryId);
    }

    public String getId() {
        return this.id;
    }

    public Class<? extends Item> getItemClass() {
        return this.itemClass;
    }

    /**
     * Retrieves the display name for the category.
     *
     * @param resources The resources associated with this application
     * @return The display name
     */
    public String getDisplayName(final Resources resources) {
        return resources.getString(this.displayNameId);
    }

    public int getCategoryImageId() {
        return this.categoryImageId;
    }
}
