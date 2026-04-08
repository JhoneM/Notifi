package com.notifi.config;

import com.notifi.infrastructure.client.EmailClient;
import com.notifi.infrastructure.client.TwilioSmsClient;
import com.notifi.infrastructure.client.TwilioWhatsAppClient;
import com.notifi.infrastructure.mock.MockEmailClient;
import com.notifi.infrastructure.mock.MockTwilioSmsClient;
import com.notifi.infrastructure.mock.MockTwilioWhatsAppClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("local")
public class LocalClientConfig {

    @Bean
    public EmailClient emailClient() {
        return new MockEmailClient(false);
    }

    @Bean
    public TwilioSmsClient twilioSmsClient() {
        return new MockTwilioSmsClient(false);
    }

    @Bean
    public TwilioWhatsAppClient twilioWhatsAppClient() {
        return new MockTwilioWhatsAppClient(false);
    }
}
