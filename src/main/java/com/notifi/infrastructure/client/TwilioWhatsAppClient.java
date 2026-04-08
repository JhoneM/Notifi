package com.notifi.infrastructure.client;

/**
 * Port interface for Twilio WhatsApp client.
 * Applies Protected Variations pattern to shield against Twilio API changes.
 */
public interface TwilioWhatsAppClient {

    /**
     * Sends a WhatsApp message via Twilio.
     *
     * @param recipientNumber the phone number of the recipient
     * @param messageBody the message body to send
     * @throws RuntimeException if the API call fails
     */
    void sendWhatsAppMessage(String recipientNumber, String messageBody);
}
