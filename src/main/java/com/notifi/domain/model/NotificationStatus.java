package com.notifi.domain.model;

/**
 * Enumeration of notification delivery statuses.
 */
public enum NotificationStatus {
    PENDING("Notification pending delivery"),
    SENT("Notification successfully sent"),
    FAILED("Notification delivery failed");

    private final String description;

    NotificationStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
