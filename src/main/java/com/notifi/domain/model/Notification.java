package com.notifi.domain.model;

import com.notifi.domain.channel.NotificationChannel;
import com.notifi.domain.client.ClientReference;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Domain entity representing a notification.
 * Manages its own state transitions and lifecycle.
 * Applies GRASP Information Expert pattern for state management.
 */
public class Notification {
    private final String id;
    private final ClientReference recipient;
    private final NotificationContent content;
    private NotificationStatus status;
    private final LocalDateTime createdAt;

    /**
     * Constructs a Notification for the given recipient and content.
     * Initializes status as PENDING and createdAt as current time.
     *
     * @param recipient the client receiving the notification
     * @param content the notification content
     * @throws NullPointerException if recipient or content is null
     */
    public Notification(ClientReference recipient, NotificationContent content) {
        this.id = UUID.randomUUID().toString();
        this.recipient = Objects.requireNonNull(recipient, "recipient cannot be null");
        this.content = Objects.requireNonNull(content, "content cannot be null");
        this.status = NotificationStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public ClientReference getRecipient() {
        return recipient;
    }

    public NotificationContent getContent() {
        return content;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Gets the preferred notification channel from the recipient.
     *
     * @return the notification channel for this notification
     */
    public NotificationChannel getChannel() {
        return recipient.getPreferredChannel();
    }

    /**
     * Marks this notification as successfully sent.
     * Transitions status from PENDING to SENT.
     */
    public void markSent() {
        this.status = NotificationStatus.SENT;
    }

    /**
     * Marks this notification as failed.
     * Transitions status from PENDING to FAILED.
     */
    public void markFailed() {
        this.status = NotificationStatus.FAILED;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id='" + id + '\'' +
                ", recipient=" + recipient +
                ", content=" + content +
                ", status=" + status +
                ", createdAt=" + createdAt +
                '}';
    }
}
