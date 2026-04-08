package com.notifi.infrastructure.resolver;

import com.notifi.domain.channel.NotificationChannel;
import com.notifi.domain.port.ChannelSenderResolver;
import com.notifi.domain.port.NotificationSender;
import com.notifi.exception.UnsupportedChannelException;
import java.util.Map;
import java.util.Objects;

/**
 * Concrete implementation of ChannelSenderResolver using a registry pattern.
 * Maintains a map of channels to their corresponding senders.
 */
public class ChannelSenderResolverImpl implements ChannelSenderResolver {
    private final Map<NotificationChannel, NotificationSender> senderRegistry;

    /**
     * Constructs a ChannelSenderResolverImpl with the given sender registry.
     *
     * @param senderRegistry a map of channels to their corresponding senders
     * @throws NullPointerException if senderRegistry is null
     */
    public ChannelSenderResolverImpl(Map<NotificationChannel, NotificationSender> senderRegistry) {
        this.senderRegistry = Objects.requireNonNull(senderRegistry, "senderRegistry cannot be null");
    }

    @Override
    public NotificationSender resolve(NotificationChannel channel) {
        Objects.requireNonNull(channel, "channel cannot be null");

        NotificationSender sender = senderRegistry.get(channel);
        if (sender == null) {
            throw new UnsupportedChannelException(
                "No sender configured for channel: " + channel.getDisplayName()
            );
        }
        return sender;
    }
}
