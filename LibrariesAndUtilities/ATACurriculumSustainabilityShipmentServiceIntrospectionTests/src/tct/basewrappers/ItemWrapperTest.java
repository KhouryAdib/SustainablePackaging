package tct.basewrappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemWrapperTest {
    private String asin;
    private String description;
    private BigDecimal length;
    private BigDecimal width;
    private BigDecimal height;
    private ItemWrapper itemWrapper;

    @BeforeEach
    private void setup() {
        asin = "123456789";
        description = "A wonderfully wonderful item";
        length = new BigDecimal(10);
        width = new BigDecimal(20);
        height = new BigDecimal(30);

        itemWrapper = ItemWrapper.builder()
            .withAsin(asin)
            .withDescription(description)
            .withLength(length)
            .withWidth(width)
            .withHeight(height)
            .build();
    }


    @Test
    void itemConstructor_isCallable() {
        // GIVEN - Item instance
        Object item = itemWrapper.getWrappedInstance();

        // WHEN + THEN - no exception
        new ItemWrapper(item);
    }

    @Test
    void getAsin_returnsCorrectAsin() {
        // GIVEN - item with known ASIN
        // WHEN
        String result = itemWrapper.getAsin();

        // THEN
        assertEquals(asin, result, "Expected ItemWrapper's getAsin to return correct ASIN");
    }

    @Test
    void getDescription_returnsCorrectDescription() {
        // GIVEN - item with known description
        // WHEN
        String result = itemWrapper.getDescription();
        // THEN
        assertEquals(description, result, "Expected ItemWrapper's getDescription to return correct description");
    }

    @Test
    void getLength_returnsCorrectLength() {
        // GIVEN - item with known length
        // WHEN
        BigDecimal result = itemWrapper.getLength();
        // THEN
        assertEquals(length, result, "Expected ItemWrapper's getLength to return correct length");
    }

    @Test
    void getWidth_returnsCorrectWidth() {
        // GIVEN - item with known width
        // WHEN
        BigDecimal result = itemWrapper.getWidth();
        // THEN
        assertEquals(width, result, "Expected ItemWrapper's getWidth to return correct width");
    }

    @Test
    void getHeight_returnsCorrectHeight() {
        // GIVEN - item with known height
        // WHEN
        BigDecimal result = itemWrapper.getHeight();
        // THEN
        assertEquals(height, result, "Expected ItemWrapper's getHeight to return correct height");
    }
}
