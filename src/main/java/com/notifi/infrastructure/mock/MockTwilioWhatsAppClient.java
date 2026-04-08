package com.notifi.infrastructure.mock;

import com.notifi.infrastructure.client.TwilioWhatsAppClient;
import java.util.logging.Logger;

/**
 * Mock implementation of TwilioWhatsAppClient for testing without actual Twilio API calls.
 * Uses standard Java logging to output messages.
 */
public class MockTwilioWhatsAppClient implements TwilioWhatsAppClient {
    private static final Logger logger = Logger.getLogger(MockTwilioWhatsAppClient.class.getName());
    private final boolean simulateFailure;

    /**
     * Constructs a MockTwilioWhatsAppClient.
     *
     * @param simulateFailure if true, will throw RuntimeException on send
     */
    public MockTwilioWhatsAppClient(boolean simulateFailure) {
        this.simulateFailure = simulateFailure;
    }

    @Override
    public void sendWhatsAppMessage(String recipientNumber, String messageBody) {
        if (simulateFailure) {
            throw new RuntimeException("Mock simulated failure");
        }
        String logMessage = String.format("[MOCK][WHATSAPP] To: %s | Body: %s", recipientNumber, messageBody);
        logger.info(logMessage);
    }
}
