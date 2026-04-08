package com.notifi.config;

import com.notifi.application.NotificationService;
import com.notifi.domain.channel.NotificationChannel;
import com.notifi.domain.port.ChannelSenderResolver;
import com.notifi.domain.port.NotificationSender;
import com.notifi.infrastructure.client.EmailClient;
import com.notifi.infrastructure.client.TwilioSmsClient;
import com.notifi.infrastructure.client.TwilioWhatsAppClient;
import com.notifi.infrastructure.resolver.ChannelSenderResolverImpl;
import com.notifi.infrastructure.sender.email.EmailSender;
import com.notifi.infrastructure.sender.sms.SmsSender;
import com.notifi.infrastructure.sender.whatsapp.WhatsAppSender;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.HashMap;
import java.util.Map;

/**
 * Configuration class for the notification module.
 * Applies Factory Method pattern as the single point of wiring and composition.
 * Enables profile-based dependency injection with Spring.
 */
@Configuration
public class NotificationModuleConfig {

    @Bean("emailSender")
    public NotificationSender emailSender(EmailClient emailClient) {
        return new EmailSender(emailClient);
    }

    @Bean("smsSender")
    public NotificationSender smsSender(TwilioSmsClient smsClient) {
        return new SmsSender(smsClient);
    }

    @Bean("whatsAppSender")
    public NotificationSender whatsAppSender(TwilioWhatsAppClient whatsAppClient) {
        return new WhatsAppSender(whatsAppClient);
    }

    @Bean
    public ChannelSenderResolver channelSenderResolver(
        @Qualifier("emailSender") NotificationSender emailSender,
        @Qualifier("smsSender") NotificationSender smsSender,
        @Qualifier("whatsAppSender") NotificationSender whatsAppSender
    ) {
        Map<NotificationChannel, NotificationSender> senderRegistry = new HashMap<>();
        senderRegistry.put(NotificationChannel.EMAIL, emailSender);
        senderRegistry.put(NotificationChannel.SMS, smsSender);
        senderRegistry.put(NotificationChannel.WHATSAPP, whatsAppSender);
        return new ChannelSenderResolverImpl(senderRegistry);
    }

    @Bean
    public NotificationService notificationService(ChannelSenderResolver channelSenderResolver) {
        return new NotificationService(channelSenderResolver);
    }
}
