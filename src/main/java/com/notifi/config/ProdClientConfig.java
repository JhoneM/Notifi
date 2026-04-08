package com.notifi.config;

import com.notifi.infrastructure.client.EmailClient;
import com.notifi.infrastructure.client.TwilioSmsClient;
import com.notifi.infrastructure.client.TwilioWhatsAppClient;
import com.notifi.infrastructure.client.EmailClientImpl;
import com.notifi.infrastructure.client.TwilioSmsClientImpl;
import com.notifi.infrastructure.client.TwilioWhatsAppClientImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("prod")
public class ProdClientConfig {

    @Bean
    public EmailClient emailClient() {
        return new EmailClientImpl();
    }

    @Bean
    public TwilioSmsClient twilioSmsClient() {
        return new TwilioSmsClientImpl();
    }

    @Bean
    public TwilioWhatsAppClient twilioWhatsAppClient() {
        return new TwilioWhatsAppClientImpl();
    }
}
