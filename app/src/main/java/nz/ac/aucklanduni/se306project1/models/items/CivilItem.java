package nz.ac.aucklanduni.se306project1.models.items;

import java.util.List;

import nz.ac.aucklanduni.se306project1.models.enums.CivilSubcategory;

public class CivilItem extends Item {
    private CivilSubcategory subcategory;

    public CivilItem() {
    }

    public CivilItem(
            final String id,
            final String displayName,
            final String categoryId,
            final String description,
            final double price,
            final List<ColouredItemInformation> colours,
            final CivilSubcategory subcategory
    ) {
        super(id, displayName, categoryId, description, price, colours);
        this.subcategory = subcategory;
    }

    public CivilSubcategory getSubcategory() {
        return this.subcategory;
    }


}
