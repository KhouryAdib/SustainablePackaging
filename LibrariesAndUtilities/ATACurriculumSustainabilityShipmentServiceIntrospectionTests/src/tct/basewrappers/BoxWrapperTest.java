package tct.basewrappers;

import com.amazon.ata.types.PackagingFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.amazon.ata.test.assertions.AtaAssertions.assertClose;

public class BoxWrapperTest {
    private BoxWrapper boxWrapper;

    @BeforeEach
    private void setup() {
        if (skipTests()) {
            return;
        }
        boxWrapper = PackagingFactory.boxWrapperOfAnyDimensions();
    }

    @Test
    void getLength_isCallable() {
        if (skipTests()) {
            return;
        }

        // GIVEN - BoxWrapper instance
        // WHEN + THEN - no assert failure
        boxWrapper.getLength();
    }

    @Test
    void getWidth_isCallable() {
        if (skipTests()) {
            return;
        }

        // GIVEN - BoxWrapper instance
        // WHEN + THEN - no assert failure
        boxWrapper.getWidth();
    }

    @Test
    void getHeight_isCallable() {
        if (skipTests()) {
            return;
        }

        // GIVEN - BoxWrapper instance
        // WHEN + THEN - no assert failure
        boxWrapper.getHeight();
    }

    @Test
    void getMass_agreesWithComputation() {
        if (skipTests()) {
            return;
        }

        // GIVEN - BoxWrapper instance
        // WHEN
        BigDecimal result = boxWrapper.getMass();

        // THEN - mass matches expected mass
        BigDecimal expectedMass = BoxWrapper.computeMass(boxWrapper);
        assertClose(
            expectedMass,
            result,
            String.format(
                "Expected box (%s) mass to be %s but was %s",
                boxWrapper.getWrappedInstance().toString(),
                expectedMass,
                result)
        );
    }

    private boolean skipTests() {
        return !BoxWrapper.testsEnabled();
    }
}
