package nz.ac.aucklanduni.se306project1.models.enums;

import java.util.HashMap;
import java.util.Map;

public enum Category {
    CIVIL("civil"),
    SOFTWARE("software"),
    CHEMMAT("chemmat"),
    MECHANICAL("mechanical");

    public static final Map<String, Category> mappedCategories = new HashMap<>();

    static {
        for (final Category category : values()) {
            mappedCategories.put(category.getId(), category);
        }
    }

    private final String id;

    Category(final String id) {
        this.id = id;
    }

    public static Category fromId(final String categoryId) {
        return mappedCategories.get(categoryId);
    }

    public String getId() {
        return this.id;
    }
}
