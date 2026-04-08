package com.notifi.exception;

/**
 * Exception thrown when notification delivery fails.
 */
public class NotificationDeliveryException extends RuntimeException {

    /**
     * Constructs a NotificationDeliveryException with message and cause.
     *
     * @param message the exception message
     * @param cause the underlying cause
     */
    public NotificationDeliveryException(String message, Throwable cause) {
        super(message, cause);
    }
}
