package tct.basewrappers;

import com.amazon.ata.types.PackagingFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class WeightedCostStrategyWrapperTest {
    private WeightedCostStrategyWrapper weightedCostStrategyWrapper;
    private MonetaryCostStrategyWrapper monetaryCostStrategyWrapper;
    private CarbonCostStrategyWrapper carbonCostStrategyWrapper;

    private ItemWrapper itemWrapper;
    private PackagingWrapper packagingWrapper;
    private FulfillmentCenterWrapper fulfillmentCenterWrapper;
    private ShipmentOptionWrapper shipmentOptionWrapper;

    @BeforeEach
    private void setup() {
        if (skipTests()) {
            return;
        }

        initMocks(this);

        monetaryCostStrategyWrapper = new MonetaryCostStrategyWrapper();
        carbonCostStrategyWrapper = new CarbonCostStrategyWrapper();
        weightedCostStrategyWrapper =
            new WeightedCostStrategyWrapper(monetaryCostStrategyWrapper, carbonCostStrategyWrapper);

        itemWrapper = ItemWrapper.builder()
            .withAsin("012345678")
            .withDescription("A woefully wistful item")
            .withLength(new BigDecimal(1))
            .withWidth(new BigDecimal(2))
            .withHeight(new BigDecimal(3))
            .build();
        packagingWrapper = PackagingFactory.anyPackagingWrapper();
        fulfillmentCenterWrapper = new FulfillmentCenterWrapper("IAD2");
        shipmentOptionWrapper = ShipmentOptionWrapper.builder()
            .withItem(itemWrapper)
            .withPackaging(packagingWrapper)
            .withFulfillmentCenter(fulfillmentCenterWrapper)
            .build();
    }

    @Test
    void weightedCostStrategyConstructor_isCallable() {
        if (skipTests()) {
            return;
        }

        // GIVEN - WeightedCostStrategy instance
        Object weightedCostStrategy = weightedCostStrategyWrapper.getWrappedInstance();

        // WHEN + THEN - no assert failure
        new WeightedCostStrategyWrapper(weightedCostStrategy);
    }

    @Test
    void MonetaryAndCarbonCostStrategyWrappersConstructor_isCallable() {
        if (skipTests()) {
            return;
        }

        // GIVEN - MonetaryCostStrategyWrapper, CarbonCostStrategyWrapper
        // WHEN + THEN - no assert failure
        new WeightedCostStrategyWrapper(monetaryCostStrategyWrapper, carbonCostStrategyWrapper);
    }

    @Test
    void getName_returnsCorrectName() {
        if (skipTests()) {
            return;
        }

        // GIVEN - WeightedCostStrategyWrapper
        // expected name
        String expectedCostStrategyname = "weighted-cost";

        // WHEN
        String result = weightedCostStrategyWrapper.getName();

        // THEN
        assertEquals(
            expectedCostStrategyname,
            result,
            String.format(
                "Expected WeightedCostStrategy#getName() to return '%s' but got '%s'", expectedCostStrategyname, result)
        );
    }

    @Test
    void getCost_invokesGetCostMethodOnWrappedInstance() {
        if (skipTests()) {
            return;
        }

        // GIVEN
        WeightedCostStrategyWrapper weightedCostStrategyWrapperSpy = spy(weightedCostStrategyWrapper);
        when(weightedCostStrategyWrapperSpy.invokeInstanceMethodWithReturnValue(
            weightedCostStrategyWrapper.getGetCostMethod(),
            shipmentOptionWrapper.getWrappedInstance())
        ).thenReturn(BigDecimal.ONE);

        // WHEN
        weightedCostStrategyWrapperSpy.getCost(shipmentOptionWrapper);

        // THEN - we call wrapped objects getCost() method - kind of the best we can do without a lot of work...
        verify(weightedCostStrategyWrapperSpy)
            .invokeInstanceMethodWithReturnValue(
                weightedCostStrategyWrapper.getGetCostMethod(),
                shipmentOptionWrapper.getWrappedInstance()
            );
    }

    private boolean skipTests() {
        return !WeightedCostStrategyWrapper.testsEnabled();
    }
}
