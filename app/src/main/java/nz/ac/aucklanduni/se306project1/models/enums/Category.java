package nz.ac.aucklanduni.se306project1.models.enums;

import android.content.res.Resources;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import java.util.HashMap;
import java.util.Map;

import nz.ac.aucklanduni.se306project1.R;
import nz.ac.aucklanduni.se306project1.builders.ui.CategoryFilterBuilder;
import nz.ac.aucklanduni.se306project1.builders.ui.MeltingPointFilterBuilder;
import nz.ac.aucklanduni.se306project1.builders.ui.SubcategoryFilterBuilder;
import nz.ac.aucklanduni.se306project1.models.items.ChemmatItem;
import nz.ac.aucklanduni.se306project1.models.items.CivilItem;
import nz.ac.aucklanduni.se306project1.models.items.Item;
import nz.ac.aucklanduni.se306project1.models.items.MechanicalItem;
import nz.ac.aucklanduni.se306project1.models.items.SoftwareItem;

// TODO: Refactor this, possibly into classes? They're starting to get pretty chunky
public enum Category {
    CIVIL("civil", CivilItem.class, R.string.civil, R.drawable.civil_category_image,
            new SubcategoryFilterBuilder<>(CivilItem.class, CivilSubcategory.values(), CivilItem::getSubcategory)),

    SOFTWARE("software", SoftwareItem.class, R.string.software, R.drawable.software_category_image,
            new SubcategoryFilterBuilder<>(SoftwareItem.class, SoftwareSubcategory.values(), SoftwareItem::getSubcategory)),

    CHEMMAT("chemmat", ChemmatItem.class, R.string.chemmat, R.drawable.chemmat_category_image, new MeltingPointFilterBuilder()),

    MECHANICAL("mechanical", MechanicalItem.class, R.string.mechanical, R.drawable.mechanical_category_image),
    ALL_ITEMS("all_items", Item.class, R.string.all_items, R.drawable.all_items_category_image);

    public static final Map<String, Category> mappedCategories = new HashMap<>();

    static {
        for (final Category category : values()) {
            mappedCategories.put(category.getId(), category);
        }
    }

    private final String id;
    private final Class<? extends Item> itemClass;
    private final int displayNameId;
    private final int categoryImageId;
    @Nullable
    private final CategoryFilterBuilder categoryFilterBuilder;

    Category(
            final String id,
            final Class<? extends Item> itemClass,
            final int displayNameId,
            final int categoryImageId
    ) {
        this(id, itemClass, displayNameId, categoryImageId, null);
    }

    Category(
            final String id,
            final Class<? extends Item> itemClass,
            @StringRes final int displayNameId,
            @DrawableRes final int categoryImageId,
            @Nullable final CategoryFilterBuilder categoryFilterBuilder) {
        this.id = id;
        this.itemClass = itemClass;
        this.displayNameId = displayNameId;
        this.categoryImageId = categoryImageId;
        this.categoryFilterBuilder = categoryFilterBuilder;
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

    @Nullable
    public CategoryFilterBuilder getCategoryFilterBuilder() {
        return this.categoryFilterBuilder;
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

    @DrawableRes
    public int getCategoryImageId() {
        return this.categoryImageId;
    }
}
