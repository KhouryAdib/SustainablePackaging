package com.amazon.ata.exceptions;

/**
 * Exception to throw when a FulfillmentCenter is null or not found in the datastore.
 */
public class UnknownFulfillmentCenterException extends Exception {
    private static final long serialVersionUID = 1465348551336896469L;

    /**
     * Exception with no message or cause.
     */
    public UnknownFulfillmentCenterException() {
        super();
    }

    /**
     * Exception with a message, but no cause.
     * @param message A descriptive message for this exception.
     */
    public UnknownFulfillmentCenterException(String message) {
        super(message);
    }

    /**
     * Exception with no message, but with a cause.
     * @param cause The original throwable resulting in this exception.
     */
    public UnknownFulfillmentCenterException(Throwable cause) {
        super(cause);
    }

    /**
     * Exception with message and cause.
     * @param message A descriptive message for this exception.
     * @param cause The original throwable resulting in this exception.
     */
    public UnknownFulfillmentCenterException(String message, Throwable cause) {
        super(message, cause);
    }
}
