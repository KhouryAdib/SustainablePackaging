package tct.basewrappers;

import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CostStrategyWrapperTest {
    @Test
    void getCostStrategyWrapperForCostStrategy_withCarbonCostStrategy_returnsCarbonCostStrategyWrapper() {
        if (skipTests()) {
            return;
        }

        // GIVEN - CarbonCostStrategy instance
        Object carbonCostStrategy = new CarbonCostStrategyWrapper().getWrappedInstance();

        // WHEN
        CostStrategyWrapper costStrategyWrapper =
            CostStrategyWrapper.getCostStrategyWrapperForCostStrategy(carbonCostStrategy);

        // THEN
        assertEquals(
            CarbonCostStrategyWrapper.class,
            costStrategyWrapper.getClass(),
            String.format(
                "Expected getCostStrategyWrapperForCostStrategy(%s) to return a %s, but was a %s instead",
                CarbonCostStrategyWrapper.getWrappedClassStatic().getSimpleName(),
                CarbonCostStrategyWrapper.class.getSimpleName(),
                costStrategyWrapper.getClass().getSimpleName())
        );
    }

    @Test
    void getCostStrategyWrapperForCostStrategy_withMonetaryCostStrategy_returnsMonetaryCostStrategy() {
        if (skipTests()) {
            return;
        }

        // GIVEN - MonetaryCostStrategy instance
        Object monetaryCostStrategy = new MonetaryCostStrategyWrapper().getWrappedInstance();

        // WHEN
        CostStrategyWrapper costStrategyWrapper =
            CostStrategyWrapper.getCostStrategyWrapperForCostStrategy(monetaryCostStrategy);

        // THEN
        assertEquals(
            MonetaryCostStrategyWrapper.class,
            costStrategyWrapper.getClass(),
            String.format(
                "Expected getCostStrategyWrapperForCostStrategy(%s) to return a %s, but was a %s instead",
                MonetaryCostStrategyWrapper.getWrappedClassStatic().getSimpleName(),
                MonetaryCostStrategyWrapper.class.getSimpleName(),
                costStrategyWrapper.getClass().getSimpleName())
        );
    }

    @Test
    void getCostStrategyWrapperForCostStrategy_withNull_assertFires() {
        if (skipTests()) {
            return;
        }

        // GIVEN - null CarbonCostStrategy
        // WHEN + THEN - assert failure
        assertThrows(
            AssertionFailedError.class,
            () -> CarbonCostStrategyWrapper.getCostStrategyWrapperForCostStrategy(null)
        );
    }

    @Test
    void getCostStrategyWrapperForCostStrategy_withOtherObject_assertFires() {
        if (skipTests()) {
            return;
        }

        // GIVEN - CarbonCostStrategy**Wrapper** instance
        CostStrategyWrapper wrongType = new CarbonCostStrategyWrapper();

        // WHEN + THEN - assert failure
        assertThrows(
            AssertionFailedError.class,
            () -> CarbonCostStrategyWrapper.getCostStrategyWrapperForCostStrategy(wrongType)
        );
    }

    private boolean skipTests() {
        return !CarbonCostStrategyWrapper.testsEnabled() ||
            !MonetaryCostStrategyWrapper.testsEnabled();
    }
}
