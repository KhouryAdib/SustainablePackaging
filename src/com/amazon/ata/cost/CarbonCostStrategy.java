package com.amazon.ata.cost;

import com.amazon.ata.types.Material;
import com.amazon.ata.types.Packaging;
import com.amazon.ata.types.ShipmentCost;
import com.amazon.ata.types.ShipmentOption;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CarbonCostStrategy implements CostStrategy {

    private static final BigDecimal LABOR_COST = BigDecimal.valueOf(0.43);
    Map<Material, BigDecimal> sustainabilityIndex;

    /**
     * Cost Strategy for carbon.
     */
    public CarbonCostStrategy() {
        sustainabilityIndex = new HashMap<>();
        sustainabilityIndex.put(Material.CORRUGATE, BigDecimal.valueOf(.017));
        sustainabilityIndex.put(Material.LAMINATED_PLASTIC, BigDecimal.valueOf(.012));
    }

    @Override
    public ShipmentCost getCost(ShipmentOption shipmentOption) {
        Packaging packaging = shipmentOption.getPackaging();
        BigDecimal materialCost = this.sustainabilityIndex.get(packaging.getMaterial());

        BigDecimal cost = packaging.getMass().multiply(materialCost);


        return new ShipmentCost(shipmentOption, cost);
    }
}
