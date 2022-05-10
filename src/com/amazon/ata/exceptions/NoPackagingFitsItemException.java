package com.amazon.ata.exceptions;

/**
 * Exception to throw when an Item is null or exceeds the size of all available packaging at its FC.
 */
public class NoPackagingFitsItemException extends Exception {

    private static final long serialVersionUID = -4967050980004572814L;

    /**
     * Exception with no message or cause.
     */
    public NoPackagingFitsItemException() {
        super();
    }

    /**
     * Exception with a message, but no cause.
     * @param message A descriptive message for this exception.
     */
    public NoPackagingFitsItemException(String message) {
        super(message);
    }

    /**
     * Exception with no message, but with a cause.
     * @param cause The original throwable resulting in this exception.
     */
    public NoPackagingFitsItemException(Throwable cause) {
        super(cause);
    }

    /**
     * Exception with message and cause.
     * @param message A descriptive message for this exception.
     * @param cause The original throwable resulting in this exception.
     */
    public NoPackagingFitsItemException(String message, Throwable cause) {
        super(message, cause);
    }
}
