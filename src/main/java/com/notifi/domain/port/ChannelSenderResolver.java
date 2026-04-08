package com.notifi.domain.port;

import com.notifi.domain.channel.NotificationChannel;

/**
 * Port interface for resolving channel-specific notification senders.
 * Applies Registry pattern through concrete implementations.
 */
public interface ChannelSenderResolver {

    /**
     * Resolves the appropriate NotificationSender for the given channel.
     *
     * @param channel the notification channel
     * @return the sender for the channel
     * @throws com.notifi.exception.UnsupportedChannelException if channel is not supported
     */
    NotificationSender resolve(NotificationChannel channel);
}
