package com.notifi.domain.client;

import com.notifi.domain.channel.NotificationChannel;
import java.util.Objects;

/**
 * Value object representing a client reference with preferred notification channel.
 * Immutable and serves as a client identifier within the notification domain.
 */
public final class ClientReference {
    private final String clientId;
    private final NotificationChannel preferredChannel;

    /**
     * Constructs a ClientReference with the given client ID and preferred channel.
     *
     * @param clientId the unique client identifier
     * @param preferredChannel the client's preferred notification channel
     * @throws NullPointerException if clientId or preferredChannel is null
     * @throws IllegalArgumentException if clientId does not match the format required by the channel
     */
    public ClientReference(String clientId, NotificationChannel preferredChannel) {
        this.clientId = Objects.requireNonNull(clientId, "clientId cannot be null");
        this.preferredChannel = Objects.requireNonNull(preferredChannel, "preferredChannel cannot be null");
        validateClientIdForChannel(clientId, preferredChannel);
    }

    /**
     * Validates the clientId format according to the notification channel.
     *
     * @param clientId the client identifier to validate
     * @param channel the notification channel
     * @throws IllegalArgumentException if the clientId format is invalid for the given channel
     */
    private void validateClientIdForChannel(String clientId, NotificationChannel channel) {
        switch (channel) {
            case EMAIL:
                validateEmailFormat(clientId);
                break;
            case SMS:
            case WHATSAPP:
                validateE164Format(clientId);
                break;
        }
    }

    /**
     * Validates that clientId matches email format: user@domain.ext
     *
     * @param clientId the email address to validate
     * @throws IllegalArgumentException if the format is invalid
     */
    private void validateEmailFormat(String clientId) {
        String emailRegex = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$";
        if (!clientId.matches(emailRegex)) {
            throw new IllegalArgumentException(
                "Invalid clientId for EMAIL channel: '" + clientId + "'. Expected format: user@domain.com"
            );
        }
    }

    /**
     * Validates that clientId matches E.164 format: +<country_code><number>
     *
     * @param clientId the phone number to validate
     * @throws IllegalArgumentException if the format is invalid
     */
    private void validateE164Format(String clientId) {
        String e164Regex = "^\\+\\d+$";
        if (!clientId.matches(e164Regex)) {
            throw new IllegalArgumentException(
                "Invalid clientId for " + preferredChannel + " channel: '" + clientId + "'. Expected format: +<country_code><number>"
            );
        }
    }

    public String getClientId() {
        return clientId;
    }

    public NotificationChannel getPreferredChannel() {
        return preferredChannel;
    }
}
