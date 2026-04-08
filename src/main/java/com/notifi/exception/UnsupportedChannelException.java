package com.notifi.exception;

/**
 * Exception thrown when an unsupported notification channel is requested.
 */
public class UnsupportedChannelException extends RuntimeException {

    /**
     * Constructs an UnsupportedChannelException with the given message.
     *
     * @param message the exception message
     */
    public UnsupportedChannelException(String message) {
        super(message);
    }
}
