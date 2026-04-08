package com.notifi.domain.model;

/**
 * Enumeration of supported content types for notifications.
 */
public enum ContentType {
    PLAIN_TEXT("text/plain"),
    HTML("text/html");

    private final String mimeType;

    ContentType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getMimeType() {
        return mimeType;
    }

    public static ContentType fromString(String value) {
        try {
            return valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                "Invalid contentType: '" + value + "'. Valid values: PLAIN_TEXT, HTML"
            );
        }
    }
}
