package com.amazon.ata.types;

import java.math.BigDecimal;
import java.util.Objects;

public class PolyBag extends Packaging {

    private BigDecimal volume;


    /**
     * declaires the polybag.
     * @param material   - the material of the package
     * @param x X
     * @param y Y
     * @param z Z
     */
    public PolyBag(Material material, BigDecimal x, BigDecimal y, BigDecimal z) {
        super(material);
        this.volume = x.multiply(y).multiply(z);
    }

    /**
     * Makes a polybag.
     * @param material   - the material of the package
     * @param volume Volume
     */
    public PolyBag(Material material, BigDecimal volume) {
        super(material);
        this.volume = volume;
    }

    public BigDecimal getVolume() {
        return volume;
    }



    @Override
    public boolean canFitItem(Item item) {
        BigDecimal itemVolume = item.getHeight().multiply(item.getLength()).multiply(item.getWidth());
        return volume.compareTo(itemVolume) >= 0;
    }

    @Override
    public BigDecimal getMass() {
        double volumeAsDouble = volume.doubleValue();
        BigDecimal bigDecimal = new BigDecimal(Math.ceil(Math.sqrt(volumeAsDouble) * 0.6));
        return bigDecimal;
    }

    /**
     * Checks if greater, equal, or lower.
     * @param o Object
     * @return bool
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        PolyBag polyBag = (PolyBag) o;
        return volume.equals(polyBag.volume);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMaterial(), volume);
    }
}
