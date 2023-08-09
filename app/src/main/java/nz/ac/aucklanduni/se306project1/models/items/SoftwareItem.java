package nz.ac.aucklanduni.se306project1.models.items;

import java.util.List;

import nz.ac.aucklanduni.se306project1.models.enums.SoftwareSubcategory;

public class SoftwareItem extends Item {

    private SoftwareSubcategory subcategory;

    public SoftwareItem() {
    }

    public SoftwareItem(
            final String id,
            final String displayName,
            final String categoryId,
            final String description,
            final double price,
            final List<ColouredItemInformation> colours,
            final SoftwareSubcategory subcategory
    ) {
        super(id, displayName, categoryId, description, price, colours);
        this.subcategory = subcategory;
    }
}
