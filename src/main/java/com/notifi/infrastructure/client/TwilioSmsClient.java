package com.notifi.infrastructure.client;

/**
 * Port interface for Twilio SMS client.
 * Applies Protected Variations pattern to shield against Twilio API changes.
 */
public interface TwilioSmsClient {

    /**
     * Sends an SMS message via Twilio.
     *
     * @param recipientNumber the phone number of the recipient
     * @param messageBody the message body to send
     * @throws RuntimeException if the API call fails
     */
    void sendSms(String recipientNumber, String messageBody);
}
