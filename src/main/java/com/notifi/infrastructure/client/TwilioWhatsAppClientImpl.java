package com.notifi.infrastructure.client;

import java.util.logging.Logger;

public class TwilioWhatsAppClientImpl implements TwilioWhatsAppClient {
    private static final Logger logger = Logger.getLogger(TwilioWhatsAppClientImpl.class.getName());

    @Override
    public void sendWhatsAppMessage(String recipientPhoneNumber, String messageBody) {
        logger.info("[PROD STUB][WHATSAPP] Sending WhatsApp to: " + recipientPhoneNumber + ", Body: " + messageBody);
    }
}
