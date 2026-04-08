package com.notifi.application;

import com.notifi.domain.client.ClientReference;
import com.notifi.domain.model.Notification;
import com.notifi.domain.model.NotificationContent;
import com.notifi.domain.model.NotificationStatus;
import com.notifi.domain.port.ChannelSenderResolver;
import com.notifi.domain.port.NotificationSender;
import com.notifi.exception.NotificationDeliveryException;
import com.notifi.exception.UnsupportedChannelException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NotificationService {
    private static final Logger logger = Logger.getLogger(NotificationService.class.getName());
    private final ChannelSenderResolver channelResolver;

    public NotificationService(ChannelSenderResolver channelResolver) {
        this.channelResolver = Objects.requireNonNull(channelResolver, "channelResolver cannot be null");
    }

    public NotificationStatus sendNotification(NotificationRequest request) {
        Objects.requireNonNull(request, "request cannot be null");

        try {
            ClientReference recipient = new ClientReference(request.getClientId(), request.getPreferredChannel());
            NotificationContent content = new NotificationContent(request.getMessageBody(), request.getContentType());
            Notification notification = new Notification(recipient, content);

            NotificationSender sender = channelResolver.resolve(notification.getChannel());
            sender.send(notification);

            logger.info("Notification " + notification.getId() + " sent successfully");
            return notification.getStatus();

        } catch (IllegalArgumentException e) {
            logger.log(Level.WARNING, "Invalid request data: " + e.getMessage());
            throw e;

        } catch (UnsupportedChannelException e) {
            logger.log(Level.WARNING, "Unsupported notification channel: " + e.getMessage());
            throw e;

        } catch (NotificationDeliveryException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            return NotificationStatus.FAILED;

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected error during notification", e);
            return NotificationStatus.FAILED;
        }
    }
}
