package com.notifi.infrastructure.client;

import java.util.logging.Logger;

public class TwilioSmsClientImpl implements TwilioSmsClient {
    private static final Logger logger = Logger.getLogger(TwilioSmsClientImpl.class.getName());

    @Override
    public void sendSms(String recipientPhoneNumber, String messageBody) {
        logger.info("[PROD STUB][SMS] Sending SMS to: " + recipientPhoneNumber + ", Body: " + messageBody);
    }
}
