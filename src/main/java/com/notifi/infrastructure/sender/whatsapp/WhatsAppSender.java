package com.notifi.infrastructure.sender.whatsapp;

import com.notifi.domain.model.Notification;
import com.notifi.domain.port.NotificationSender;
import com.notifi.exception.NotificationDeliveryException;
import com.notifi.infrastructure.client.TwilioWhatsAppClient;
import java.util.Objects;

/**
 * Concrete sender implementation for WhatsApp notifications.
 * Adapts TwilioWhatsAppClient interface to NotificationSender port.
 * Applies Adapter and Strategy patterns.
 */
public class WhatsAppSender implements NotificationSender {
    private final TwilioWhatsAppClient whatsAppClient;

    /**
     * Constructs a WhatsAppSender with the given Twilio WhatsApp client.
     *
     * @param whatsAppClient the Twilio WhatsApp client
     * @throws NullPointerException if whatsAppClient is null
     */
    public WhatsAppSender(TwilioWhatsAppClient whatsAppClient) {
        this.whatsAppClient = Objects.requireNonNull(whatsAppClient, "whatsAppClient cannot be null");
    }

    @Override
    public void send(Notification notification) {
        try {
            String recipientNumber = notification.getRecipient().getClientId();
            String messageBody = notification.getContent().getBody();
            whatsAppClient.sendWhatsAppMessage(recipientNumber, messageBody);
            notification.markSent();
        } catch (RuntimeException e) {
            throw new NotificationDeliveryException(
                "Failed to send WhatsApp notification to " + notification.getRecipient().getClientId(),
                e
            );
        }
    }
}
