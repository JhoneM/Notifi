package com.notifi.application;

import com.notifi.domain.channel.NotificationChannel;
import com.notifi.domain.model.ContentType;
import java.util.Objects;

/**
 * Data Transfer Object representing a notification request.
 * Carries information from the application layer to the domain.
 */
public final class NotificationRequest {
    private final String clientId;
    private final NotificationChannel preferredChannel;
    private final String messageBody;
    private final ContentType contentType;

    /**
     * Constructs a NotificationRequest with the given parameters.
     *
     * @param clientId the unique client identifier
     * @param preferredChannel the client's preferred notification channel
     * @param messageBody the message body/content
     * @param contentType the type of content
     * @throws NullPointerException if any parameter is null
     */
    public NotificationRequest(String clientId,
                              NotificationChannel preferredChannel,
                              String messageBody,
                              ContentType contentType) {
        this.clientId = Objects.requireNonNull(clientId, "clientId cannot be null");
        this.preferredChannel = Objects.requireNonNull(preferredChannel, "preferredChannel cannot be null");
        this.messageBody = Objects.requireNonNull(messageBody, "messageBody cannot be null");
        this.contentType = Objects.requireNonNull(contentType, "contentType cannot be null");
    }

    public String getClientId() {
        return clientId;
    }

    public NotificationChannel getPreferredChannel() {
        return preferredChannel;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public ContentType getContentType() {
        return contentType;
    }

    @Override
    public String toString() {
        return "NotificationRequest{" +
                "clientId='" + clientId + '\'' +
                ", preferredChannel=" + preferredChannel +
                ", messageBody='" + messageBody + '\'' +
                ", contentType=" + contentType +
                '}';
    }
}
