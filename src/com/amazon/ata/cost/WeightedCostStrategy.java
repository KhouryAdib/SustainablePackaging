package com.amazon.ata.cost;



import com.amazon.ata.types.Packaging;
import com.amazon.ata.types.ShipmentCost;
import com.amazon.ata.types.ShipmentOption;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class WeightedCostStrategy implements CostStrategy {

    private Map<CostStrategy, BigDecimal> strategyMap;

    /**
     * Description.
     * @param a MonetaryCostStrategy
     * @param b CarbonCostStrategy
     */
    public WeightedCostStrategy(MonetaryCostStrategy a , CarbonCostStrategy b) {
        strategyMap = new HashMap<>();
        strategyMap.put(a, new BigDecimal(.8));
        strategyMap.put(b, new BigDecimal(.2));
    }

    /**
     * Builder.
     * @param builder builder
     */
    public WeightedCostStrategy(Builder builder) {
        strategyMap = builder.strategyMap;
    }

    /**
     * Description.
     * @param map Map
     */
    public WeightedCostStrategy(Map<CostStrategy, BigDecimal> map) {
        strategyMap = map;
    }

    @Override
    public ShipmentCost getCost(ShipmentOption shipmentOption) {
        Packaging packaging = shipmentOption.getPackaging();
        double total = 0;

        for (Map.Entry<CostStrategy, BigDecimal> entry: strategyMap.entrySet()) {
            total += entry.getKey().getCost(shipmentOption).getCost().doubleValue() * entry.getValue().doubleValue();
        }

        return new ShipmentCost(shipmentOption, new BigDecimal(total));
    }

    /**
     * {@code ShipmentOption} builder static inner class.
     */
    public static class Builder {
        private Map<CostStrategy, BigDecimal> strategyMap;

        /**
         * Builder.
         */
        public Builder() {
        }

        /**
         * Sets the {@code item} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param costStrategy the {@code item} to set
         * @param bigDecimal the {@code item} to set
         * @return a reference to this Builder
         */
        public WeightedCostStrategy.Builder addStrategyWithWeight(CostStrategy costStrategy, BigDecimal bigDecimal) {
            strategyMap.put(costStrategy, bigDecimal);
            return this;
        }

        /**
         * Description.
         */
        public void addStrategyWithWeight(){}
        /**
         * Returns a {@code ShipmentOption} built from the parameters previously set.
         *
         * @return a {@code ShipmentOption} built with parameters of this {@code ShipmentOption.Builder}
         */
        public WeightedCostStrategy build() {
            return new WeightedCostStrategy(this);
        }
    }
}


