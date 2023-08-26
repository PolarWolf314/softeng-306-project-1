package nz.ac.aucklanduni.se306project1.models.items;

import java.util.List;

public class ChemmatItem extends Item {
    private float meltingPoint;

    public ChemmatItem() {
    }

    public ChemmatItem(final String id, final String displayName, final String categoryId, final String description, final double price, final List<ColouredItemInformation> colours, final float meltingPoint) {
        super(id, displayName, categoryId, description, price, colours);
        this.meltingPoint = meltingPoint;
    }

    public float getMeltingPoint() {
        return this.meltingPoint;
    }
}
