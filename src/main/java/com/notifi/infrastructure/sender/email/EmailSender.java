package com.notifi.infrastructure.sender.email;

import com.notifi.domain.model.Notification;
import com.notifi.domain.port.NotificationSender;
import com.notifi.exception.NotificationDeliveryException;
import com.notifi.infrastructure.client.EmailClient;
import java.util.Objects;

/**
 * Concrete sender implementation for email notifications.
 * Adapts EmailClient interface to NotificationSender port.
 * Applies Adapter and Strategy patterns.
 */
public class EmailSender implements NotificationSender {
    private final EmailClient emailClient;

    /**
     * Constructs an EmailSender with the given email client.
     *
     * @param emailClient the email client
     * @throws NullPointerException if emailClient is null
     */
    public EmailSender(EmailClient emailClient) {
        this.emailClient = Objects.requireNonNull(emailClient, "emailClient cannot be null");
    }

    @Override
    public void send(Notification notification) {
        try {
            String recipientAddress = notification.getRecipient().getClientId();
            String body = notification.getContent().getBody();
            boolean isHtml = notification.getContent().isHtml();
            String subject = isHtml ? "HTML Notification" : "";

            emailClient.sendEmail(recipientAddress, subject, body, isHtml);
            notification.markSent();
        } catch (RuntimeException e) {
            throw new NotificationDeliveryException(
                "Failed to send email notification to " + notification.getRecipient().getClientId(),
                e
            );
        }
    }
}
