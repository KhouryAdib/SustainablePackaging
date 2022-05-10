package com.amazon.ata.activity;

import com.amazon.ata.service.ShipmentService;
import com.amazon.ata.types.FulfillmentCenter;
import com.amazon.ata.types.Item;
import com.amazon.ata.types.ShipmentOption;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * PARTICIPANTS: You are not expected to modify or use this class directly. Please do not modify the code contained
 * in this class as doing so might break the Shipment Service functionality.
 *
 * This is implementation of the PrepareShipment activity. It handles PrepareShipment requests by returning
 * the appropriate shipment option.
 */
public class PrepareShipmentActivity
                      implements RequestHandler<PrepareShipmentRequest, String> {
    /**
     * Shipment service used to retrieve shipment options.
     */
    private ShipmentService shipmentService;

    /**
     * Instantiates a new defaauly PrepareShipmentActivity object.
     *      Provided because AWS Lambda needs one defined evenif it does nothing
     */
    public PrepareShipmentActivity() {}
    /**
     * Instantiates a new PrepareShipmentActivity object.
     * @param shipmentService Shipment service used to retrieve shipment options.
     */
     public PrepareShipmentActivity(ShipmentService shipmentService) {
         this.shipmentService = shipmentService;
     }

    /**
     * This method handles the incoming request by calling the shipment service and returning the
     * appropriate shipment option for the fulfillment center and item provided in the request.
     *
     * @param request contains information on fulfillment center and item
     * @return String that contains item and packaging information in JSON format
     * @throws Exception if the request can't be fulfilled
     */
    @Override
        public String handleRequest(PrepareShipmentRequest request, Context context) {

        Item item = Item.builder()
                .withAsin(request.getAsin())
                .withDescription(request.getDescription())
                .withLength(request.getLength())
                .withWidth(request.getWidth())
                .withHeight(request.getHeight())
                .build();

        FulfillmentCenter fulfillmentCenter = new FulfillmentCenter(request.getFcCode());

        ShipmentOption shipmentOption = shipmentService.findShipmentOption(item, fulfillmentCenter);

        if (shipmentOption == null) {
            return null;
        }

        // Converting the shipmentOption to JSON using ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        String shipmentOptionJSON = null;
        try {
            shipmentOptionJSON = objectMapper.writeValueAsString(shipmentOption);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return shipmentOptionJSON;
    }
}

