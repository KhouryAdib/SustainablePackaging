package com.amazon.ata.types;

import java.math.BigDecimal;
import java.util.Objects;

public class PolyBag extends Packaging{

    private BigDecimal volume;

    public BigDecimal getVolume() {
        return volume;
    }

    /**
     * Instantiates a new Packaging object.
     *
     */
    public PolyBag(Material material, BigDecimal x, BigDecimal y, BigDecimal z) {
        super(Material.LAMINATED_PLASTIC);
        this.volume = x.multiply(y).multiply(z);
    }

    public PolyBag(Material material, BigDecimal volume) {
        super(Material.LAMINATED_PLASTIC);
        this.volume = volume;
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

    @Override
    public int hashCode() {
        return Objects.hash(getMaterial(), getVolume());
    }
}
