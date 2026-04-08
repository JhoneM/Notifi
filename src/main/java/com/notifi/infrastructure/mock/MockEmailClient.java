package com.notifi.infrastructure.mock;

import com.notifi.infrastructure.client.EmailClient;
import java.util.logging.Logger;

/**
 * Mock implementation of EmailClient for testing without actual email sending.
 * Uses standard Java logging to output messages.
 */
public class MockEmailClient implements EmailClient {
    private static final Logger logger = Logger.getLogger(MockEmailClient.class.getName());
    private final boolean simulateFailure;

    /**
     * Constructs a MockEmailClient.
     *
     * @param simulateFailure if true, will throw RuntimeException on send
     */
    public MockEmailClient(boolean simulateFailure) {
        this.simulateFailure = simulateFailure;
    }

    @Override
    public void sendEmail(String recipientAddress, String subject, String body, boolean isHtml) {
        if (simulateFailure) {
            throw new RuntimeException("Mock simulated failure");
        }
        String logMessage = String.format("[MOCK][EMAIL] To: %s | Subject: %s | IsHTML: %s | Body: %s",
            recipientAddress, subject, isHtml, body);
        logger.info(logMessage);
    }
}
