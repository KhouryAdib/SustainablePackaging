package com.amazon.ata.service;

import com.amazon.ata.cost.CostStrategy;
import com.amazon.ata.cost.MonetaryCostStrategy;
import com.amazon.ata.dao.PackagingDAO;
import com.amazon.ata.datastore.PackagingDatastore;
import com.amazon.ata.exceptions.NoPackagingFitsItemException;
import com.amazon.ata.exceptions.UnknownFulfillmentCenterException;
import com.amazon.ata.types.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.mockito.ArgumentMatchers.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.MockitoAnnotations.openMocks;

class ShipmentServiceTest {

    private Item smallItem = Item.builder()
            .withHeight(BigDecimal.valueOf(1))
            .withWidth(BigDecimal.valueOf(1))
            .withLength(BigDecimal.valueOf(1))
            .withAsin("abcde")
            .build();

    private Item largeItem = Item.builder()
            .withHeight(BigDecimal.valueOf(1000))
            .withWidth(BigDecimal.valueOf(1000))
            .withLength(BigDecimal.valueOf(1000))
            .withAsin("12345")
            .build();


    private FulfillmentCenter existentFC = new FulfillmentCenter("ABE2");
    private FulfillmentCenter nonExistentFC = new FulfillmentCenter("NonExistentFC");


    @Mock
    PackagingDatastore packagingDatastore;// = Mockito.mock(PackagingDatastore.class);

    @Mock
    CostStrategy costStrategy;// = Mockito.mock(MonetaryCostStrategy.class);

    @Mock
    private PackagingDAO packagingDAO;// = Mockito.mock(PackagingDAO.class);


    @InjectMocks
    private ShipmentService shipmentService;// = Mockito.mock(ShipmentService.class);
    // = new ShipmentService(new PackagingDAO(new PackagingDatastore()), new MonetaryCostStrategy());

    @Mock
    ShipmentOption option;

    @Mock
    FulfillmentCenter fulfillmentCenter;



    @BeforeEach
    void setUp() {
        openMocks(this);




    }

    @Test
    void findBestShipmentOption_existentFCAndItemCanFit_returnsShipmentOption() throws UnknownFulfillmentCenterException, NoPackagingFitsItemException {
        List<ShipmentOption> options = new ArrayList<>();
        ShipmentOption option = ShipmentOption.builder().withItem(smallItem).withFulfillmentCenter(existentFC).build();
        options.add(option);
        ShipmentCost cost = new ShipmentCost(option, BigDecimal.valueOf(5));
        when(costStrategy.getCost(option)).thenReturn(cost);
        when(packagingDAO.findShipmentOptions(any(Item.class), any(FulfillmentCenter.class))).thenReturn(options);

        // GIVEN & WHEN
        ShipmentOption shipmentOption = shipmentService.findShipmentOption(largeItem, existentFC);

        // THEN
        assertNotNull(shipmentOption);
    }

    @Test
    void findBestShipmentOption_existentFCAndItemCannotFit_returnsShipmentOption() throws UnknownFulfillmentCenterException, NoPackagingFitsItemException {
        List<ShipmentOption> options = new ArrayList<>();
        ShipmentOption option = ShipmentOption.builder().withItem(largeItem).withFulfillmentCenter(existentFC).build();
        options.add(option);
        ShipmentCost cost = new ShipmentCost(option, BigDecimal.valueOf(5));
        when(costStrategy.getCost(option)).thenReturn(cost);
        when(packagingDAO.findShipmentOptions(any(Item.class), any(FulfillmentCenter.class))).thenReturn(options);

        // GIVEN & WHEN
        ShipmentOption shipmentOption = shipmentService.findShipmentOption(largeItem, existentFC);

        // THEN
        assertNotNull(shipmentOption);
    }

    @Test
    void findBestShipmentOption_nonExistentFCAndItemCanFit_throwsRuntimeException() throws UnknownFulfillmentCenterException, NoPackagingFitsItemException {
        // GIVEN & WHEN
        //ShipmentOption shipmentOption = shipmentService.findShipmentOption(smallItem, nonExistentFC);

        when(packagingDAO.findShipmentOptions(any(Item.class), any(FulfillmentCenter.class))).thenThrow(UnknownFulfillmentCenterException.class);


        assertThrows(RuntimeException.class, () -> {
            shipmentService.findShipmentOption(smallItem, nonExistentFC);
        }, "unknown fulfillment center");


        // THEN
        //assertNull(shipmentOption);
    }

    @Test
    void findBestShipmentOption_nonExistentFCAndItemCannotFit_throwsRuntimeException() throws UnknownFulfillmentCenterException, NoPackagingFitsItemException {
        // GIVEN & WHEN
        when(packagingDAO.findShipmentOptions(any(Item.class), any(FulfillmentCenter.class))).thenThrow(UnknownFulfillmentCenterException.class);


        assertThrows(RuntimeException.class, () -> {
            shipmentService.findShipmentOption(largeItem, nonExistentFC);
        }, "unknown fulfillment center");

        //ShipmentOption shipmentOption = shipmentService.findShipmentOption(largeItem, nonExistentFC);

        // THEN
        //assertNull(shipmentOption);
    }

}