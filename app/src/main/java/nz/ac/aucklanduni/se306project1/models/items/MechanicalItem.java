package nz.ac.aucklanduni.se306project1.models.items;

import java.util.List;

public class MechanicalItem extends Item {

    public MechanicalItem() {
    }

    public MechanicalItem(
            final String id,
            final String displayName,
            final String categoryId,
            final String description,
            final double price,
            final List<ColouredItemInformation> colours
    ) {
        super(id, displayName, categoryId, description, price, colours);
    }
}
