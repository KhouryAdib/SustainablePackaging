package tct.basewrappers;

import com.amazon.ata.types.PackagingFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.amazon.ata.test.assertions.AtaAssertions.assertClose;

public class PolyBagWrapperTest {
    private PolyBagWrapper polyBagWrapper;

    @BeforeEach
    private void setup() {
        if (skipTests()) {
            return;
        }
        polyBagWrapper = PackagingFactory.polyBagWrapperOfAnyVolume();
    }

    @Test
    void getVolume_isCallable() {
        if (skipTests()) {
            return;
        }

        // GIVEN - PolyBagWrapper instance
        // WHEN + THEN - no assert failure
        polyBagWrapper.getVolume();
    }

    @Test
    void getMass_agreesWithComputation() {
        if (skipTests()) {
            return;
        }

        // GIVEN - PolyBagWrapper instance
        // WHEN
        BigDecimal result = polyBagWrapper.getMass();

        // THEN - mass matches expected mass
        BigDecimal expectedMass = PolyBagWrapper.computeMass(polyBagWrapper);
        assertClose(
            expectedMass,
            result,
            String.format(
                "Expected polyBag (%s) mass to be %s but was %s",
                polyBagWrapper.getWrappedInstance().toString(),
                expectedMass,
                result)
        );
    }

    private boolean skipTests() {
        return !PolyBagWrapper.testsEnabled();
    }
}
