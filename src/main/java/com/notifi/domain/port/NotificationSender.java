package com.notifi.domain.port;

import com.notifi.domain.model.Notification;

/**
 * Port interface for sending notifications.
 * Defines the contract for notification senders.
 * Applies Strategy pattern for different sending implementations.
 */
public interface NotificationSender {

    /**
     * Sends the given notification.
     *
     * @param notification the notification to send
     * @throws com.notifi.exception.NotificationDeliveryException if delivery fails
     */
    void send(Notification notification);
}
