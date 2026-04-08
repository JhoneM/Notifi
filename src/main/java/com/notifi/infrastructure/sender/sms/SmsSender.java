package com.notifi.infrastructure.sender.sms;

import com.notifi.domain.model.Notification;
import com.notifi.domain.port.NotificationSender;
import com.notifi.exception.NotificationDeliveryException;
import com.notifi.infrastructure.client.TwilioSmsClient;
import java.util.Objects;

/**
 * Concrete sender implementation for SMS notifications.
 * Adapts TwilioSmsClient interface to NotificationSender port.
 * Applies Adapter and Strategy patterns.
 */
public class SmsSender implements NotificationSender {
    private final TwilioSmsClient smsClient;

    /**
     * Constructs a SmsSender with the given Twilio SMS client.
     *
     * @param smsClient the Twilio SMS client
     * @throws NullPointerException if smsClient is null
     */
    public SmsSender(TwilioSmsClient smsClient) {
        this.smsClient = Objects.requireNonNull(smsClient, "smsClient cannot be null");
    }

    @Override
    public void send(Notification notification) {
        try {
            String recipientNumber = notification.getRecipient().getClientId();
            String messageBody = notification.getContent().getBody();
            smsClient.sendSms(recipientNumber, messageBody);
            notification.markSent();
        } catch (RuntimeException e) {
            throw new NotificationDeliveryException(
                "Failed to send SMS notification to " + notification.getRecipient().getClientId(),
                e
            );
        }
    }
}
