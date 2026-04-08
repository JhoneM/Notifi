package com.notifi.domain.channel;

/**
 * Enumeration of supported notification channels.
 */
public enum NotificationChannel {
    EMAIL("Email"),
    SMS("SMS"),
    WHATSAPP("WhatsApp");

    private final String displayName;

    NotificationChannel(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static NotificationChannel fromString(String value) {
        try {
            return valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                "Invalid channel: '" + value + "'. Valid values: EMAIL, SMS, WHATSAPP"
            );
        }
    }
}
