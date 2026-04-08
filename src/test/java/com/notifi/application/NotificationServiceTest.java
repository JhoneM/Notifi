package com.notifi.application;

import com.notifi.domain.channel.NotificationChannel;
import com.notifi.domain.model.ContentType;
import com.notifi.domain.model.Notification;
import com.notifi.domain.model.NotificationStatus;
import com.notifi.domain.port.ChannelSenderResolver;
import com.notifi.domain.port.NotificationSender;
import com.notifi.exception.NotificationDeliveryException;
import com.notifi.exception.UnsupportedChannelException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doAnswer;

@ExtendWith(MockitoExtension.class)
@DisplayName("NotificationService Tests")
class NotificationServiceTest {

    @Mock
    private ChannelSenderResolver channelResolver;

    @Mock
    private NotificationSender notificationSender;

    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        notificationService = new NotificationService(channelResolver);
    }

    // ============ Happy Path Tests ============

    @Test
    @DisplayName("Should send notification successfully and return SENT status")
    void testSendNotificationSuccess() {
        NotificationRequest request = new NotificationRequest(
            "user@example.com",
            NotificationChannel.EMAIL,
            "Test message",
            ContentType.PLAIN_TEXT
        );

        when(channelResolver.resolve(NotificationChannel.EMAIL))
            .thenReturn(notificationSender);
        doAnswer(invocation -> {
            Notification notification = invocation.getArgument(0);
            notification.markSent();
            return null;
        }).when(notificationSender).send(any(Notification.class));

        NotificationStatus status = notificationService.sendNotification(request);

        assertEquals(NotificationStatus.SENT, status);
        verify(channelResolver).resolve(NotificationChannel.EMAIL);
        verify(notificationSender).send(any(Notification.class));
    }

    @Test
    @DisplayName("Should send SMS notification successfully")
    void testSendSmsNotificationSuccess() {
        NotificationRequest request = new NotificationRequest(
            "+1234567890",
            NotificationChannel.SMS,
            "SMS message",
            ContentType.PLAIN_TEXT
        );

        when(channelResolver.resolve(NotificationChannel.SMS))
            .thenReturn(notificationSender);
        doAnswer(invocation -> {
            Notification notification = invocation.getArgument(0);
            notification.markSent();
            return null;
        }).when(notificationSender).send(any(Notification.class));

        NotificationStatus status = notificationService.sendNotification(request);

        assertEquals(NotificationStatus.SENT, status);
        verify(notificationSender).send(any(Notification.class));
    }

    @Test
    @DisplayName("Should send WhatsApp notification successfully")
    void testSendWhatsappNotificationSuccess() {
        NotificationRequest request = new NotificationRequest(
            "+9876543210",
            NotificationChannel.WHATSAPP,
            "WhatsApp message",
            ContentType.PLAIN_TEXT
        );

        when(channelResolver.resolve(NotificationChannel.WHATSAPP))
            .thenReturn(notificationSender);
        doAnswer(invocation -> {
            Notification notification = invocation.getArgument(0);
            notification.markSent();
            return null;
        }).when(notificationSender).send(any(Notification.class));

        NotificationStatus status = notificationService.sendNotification(request);

        assertEquals(NotificationStatus.SENT, status);
        verify(notificationSender).send(any(Notification.class));
    }

    // ============ Failure Handling Tests ============

    @Test
    @DisplayName("Should return FAILED status when NotificationDeliveryException is thrown")
    void testSendNotificationDeliveryException() {
        NotificationRequest request = new NotificationRequest(
            "user@example.com",
            NotificationChannel.EMAIL,
            "Test message",
            ContentType.PLAIN_TEXT
        );

        when(channelResolver.resolve(NotificationChannel.EMAIL))
            .thenReturn(notificationSender);
        doThrow(new NotificationDeliveryException("Email server error", new RuntimeException("Connection refused")))
            .when(notificationSender).send(any(Notification.class));

        NotificationStatus status = notificationService.sendNotification(request);

        assertEquals(NotificationStatus.FAILED, status);
        verify(notificationSender).send(any(Notification.class));
    }

    @Test
    @DisplayName("Should return FAILED status when unexpected exception occurs")
    void testSendNotificationUnexpectedException() {
        NotificationRequest request = new NotificationRequest(
            "user@example.com",
            NotificationChannel.EMAIL,
            "Test message",
            ContentType.PLAIN_TEXT
        );

        when(channelResolver.resolve(NotificationChannel.EMAIL))
            .thenReturn(notificationSender);
        doThrow(new RuntimeException("Unexpected error"))
            .when(notificationSender).send(any(Notification.class));

        NotificationStatus status = notificationService.sendNotification(request);

        assertEquals(NotificationStatus.FAILED, status);
    }

    // ============ Exception Re-throw Tests ============

    @Test
    @DisplayName("Should rethrow IllegalArgumentException from validation")
    void testRethrowIllegalArgumentException() {
        NotificationRequest request = new NotificationRequest(
            "invalid-email",
            NotificationChannel.EMAIL,
            "Test message",
            ContentType.PLAIN_TEXT
        );

        assertThrows(IllegalArgumentException.class, () -> {
            notificationService.sendNotification(request);
        });
    }

    @Test
    @DisplayName("Should rethrow UnsupportedChannelException when channel is not supported")
    void testRethrowUnsupportedChannelException() {
        NotificationRequest request = new NotificationRequest(
            "user@example.com",
            NotificationChannel.EMAIL,
            "Test message",
            ContentType.PLAIN_TEXT
        );

        when(channelResolver.resolve(NotificationChannel.EMAIL))
            .thenThrow(new UnsupportedChannelException("No sender configured for channel: Email"));

        assertThrows(UnsupportedChannelException.class, () -> {
            notificationService.sendNotification(request);
        });
    }

    // ============ Null Safety Tests ============

    @Test
    @DisplayName("Should throw NullPointerException when request is null")
    void testSendNotificationWithNullRequest() {
        assertThrows(NullPointerException.class, () -> {
            notificationService.sendNotification(null);
        });
    }

    @Test
    @DisplayName("Should throw NullPointerException when channelResolver is null")
    void testConstructorWithNullResolver() {
        assertThrows(NullPointerException.class, () -> {
            new NotificationService(null);
        });
    }

    // ============ Integration Behavior Tests ============

    @Test
    @DisplayName("Should create Notification object with correct parameters")
    void testNotificationCreationWithCorrectParameters() {
        NotificationRequest request = new NotificationRequest(
            "user@example.com",
            NotificationChannel.EMAIL,
            "Test message",
            ContentType.HTML
        );

        when(channelResolver.resolve(NotificationChannel.EMAIL))
            .thenReturn(notificationSender);

        notificationService.sendNotification(request);

        verify(notificationSender).send(argThat(notification ->
            notification.getRecipient().getClientId().equals("user@example.com") &&
            notification.getChannel() == NotificationChannel.EMAIL &&
            notification.getContent().getBody().equals("Test message") &&
            notification.getContent().isHtml()
        ));
    }

    @Test
    @DisplayName("Should resolve correct sender for each channel")
    void testCorrectSenderResolution() {
        // Test EMAIL
        NotificationRequest emailRequest = new NotificationRequest(
            "user@example.com",
            NotificationChannel.EMAIL,
            "Email message",
            ContentType.PLAIN_TEXT
        );

        when(channelResolver.resolve(NotificationChannel.EMAIL))
            .thenReturn(notificationSender);

        notificationService.sendNotification(emailRequest);
        verify(channelResolver).resolve(NotificationChannel.EMAIL);

        // Test SMS
        reset(channelResolver, notificationSender);
        NotificationRequest smsRequest = new NotificationRequest(
            "+1234567890",
            NotificationChannel.SMS,
            "SMS message",
            ContentType.PLAIN_TEXT
        );

        when(channelResolver.resolve(NotificationChannel.SMS))
            .thenReturn(notificationSender);

        notificationService.sendNotification(smsRequest);
        verify(channelResolver).resolve(NotificationChannel.SMS);
    }
}
