package nz.ac.aucklanduni.se306project1.models.enums;

import java.util.HashMap;
import java.util.Map;

import nz.ac.aucklanduni.se306project1.models.items.ChemmatItem;
import nz.ac.aucklanduni.se306project1.models.items.CivilItem;
import nz.ac.aucklanduni.se306project1.models.items.Item;
import nz.ac.aucklanduni.se306project1.models.items.MechanicalItem;
import nz.ac.aucklanduni.se306project1.models.items.SoftwareItem;

public enum Category {
    CIVIL("civil", CivilItem.class),
    SOFTWARE("software", SoftwareItem.class),
    CHEMMAT("chemmat", ChemmatItem.class),
    MECHANICAL("mechanical", MechanicalItem.class);

    public static final Map<String, Category> mappedCategories = new HashMap<>();

    static {
        for (final Category category : values()) {
            mappedCategories.put(category.getId(), category);
        }
    }

    private final String id;
    private final Class<? extends Item> itemClass;

    Category(final String id, final Class<? extends Item> itemClass) {
        this.id = id;
        this.itemClass = itemClass;
    }

    public static Category fromId(final String categoryId) {
        return mappedCategories.get(categoryId);
    }

    public String getId() {
        return this.id;
    }

    public Class<? extends Item> getItemClass() {
        return this.itemClass;
    }
}
