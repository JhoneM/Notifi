package com.notifi.infrastructure.client;

import java.util.logging.Logger;

public class EmailClientImpl implements EmailClient {
    private static final Logger logger = Logger.getLogger(EmailClientImpl.class.getName());

    @Override
    public void sendEmail(String recipientAddress, String subject, String body, boolean isHtml) {
        logger.info("[PROD STUB][EMAIL] To: " + recipientAddress + " | Subject: " + subject + " | IsHTML: " + isHtml);
    }
}
