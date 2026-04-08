package com.notifi.infrastructure.mock;

import com.notifi.infrastructure.client.TwilioSmsClient;
import java.util.logging.Logger;

/**
 * Mock implementation of TwilioSmsClient for testing without actual Twilio API calls.
 * Uses standard Java logging to output messages.
 */
public class MockTwilioSmsClient implements TwilioSmsClient {
    private static final Logger logger = Logger.getLogger(MockTwilioSmsClient.class.getName());
    private final boolean simulateFailure;

    /**
     * Constructs a MockTwilioSmsClient.
     *
     * @param simulateFailure if true, will throw RuntimeException on send
     */
    public MockTwilioSmsClient(boolean simulateFailure) {
        this.simulateFailure = simulateFailure;
    }

    @Override
    public void sendSms(String recipientNumber, String messageBody) {
        if (simulateFailure) {
            throw new RuntimeException("Mock simulated failure");
        }
        String logMessage = String.format("[MOCK][SMS] To: %s | Body: %s", recipientNumber, messageBody);
        logger.info(logMessage);
    }
}
