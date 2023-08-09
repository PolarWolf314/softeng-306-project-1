package nz.ac.aucklanduni.se306project1.models.items;

import java.util.List;
import java.util.Objects;

public class ChemmatItem extends Item {
    private double meltingPoint;

    public ChemmatItem() {
    }

    public ChemmatItem(final String id, final String displayName, final String categoryId, final String description, final double price, final List<ColouredItemInformation> colours, final double meltingPoint) {
        super(id, displayName, categoryId, description, price, colours);
        this.meltingPoint = meltingPoint;
    }

    public double getMeltingPoint() {
        return this.meltingPoint;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof ChemmatItem)) return false;
        if (!super.equals(o)) return false;
        final ChemmatItem that = (ChemmatItem) o;
        return Double.compare(that.meltingPoint, this.meltingPoint) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.meltingPoint);
    }
}
