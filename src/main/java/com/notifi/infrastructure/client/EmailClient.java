package com.notifi.infrastructure.client;

/**
 * Port interface for email client.
 * Abstracts the email sending implementation details.
 */
public interface EmailClient {

    /**
     * Sends an email message.
     *
     * @param recipientAddress the email address of the recipient
     * @param subject the email subject
     * @param body the email body
     * @param isHtml true if body is HTML, false if plain text
     * @throws RuntimeException if the email sending fails
     */
    void sendEmail(String recipientAddress, String subject, String body, boolean isHtml);
}
