package com.notifi.domain.channel;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("NotificationChannel Tests")
class NotificationChannelTest {

    @Nested
    @DisplayName("fromString Happy Path Tests")
    class FromStringHappyPathTests {

        @Test
        @DisplayName("Should parse EMAIL in uppercase")
        void testParseEmailUppercase() {
            NotificationChannel channel = NotificationChannel.fromString("EMAIL");
            assertEquals(NotificationChannel.EMAIL, channel);
        }

        @Test
        @DisplayName("Should parse EMAIL in lowercase")
        void testParseEmailLowercase() {
            NotificationChannel channel = NotificationChannel.fromString("email");
            assertEquals(NotificationChannel.EMAIL, channel);
        }

        @Test
        @DisplayName("Should parse EMAIL in mixed case")
        void testParseEmailMixedCase() {
            NotificationChannel channel = NotificationChannel.fromString("Email");
            assertEquals(NotificationChannel.EMAIL, channel);
        }

        @Test
        @DisplayName("Should parse SMS in uppercase")
        void testParseSmsUppercase() {
            NotificationChannel channel = NotificationChannel.fromString("SMS");
            assertEquals(NotificationChannel.SMS, channel);
        }

        @Test
        @DisplayName("Should parse SMS in lowercase")
        void testParseSmsSms() {
            NotificationChannel channel = NotificationChannel.fromString("sms");
            assertEquals(NotificationChannel.SMS, channel);
        }

        @Test
        @DisplayName("Should parse SMS in mixed case")
        void testParseSmsMixedCase() {
            NotificationChannel channel = NotificationChannel.fromString("Sms");
            assertEquals(NotificationChannel.SMS, channel);
        }

        @Test
        @DisplayName("Should parse WHATSAPP in uppercase")
        void testParseWhatsappUppercase() {
            NotificationChannel channel = NotificationChannel.fromString("WHATSAPP");
            assertEquals(NotificationChannel.WHATSAPP, channel);
        }

        @Test
        @DisplayName("Should parse WHATSAPP in lowercase")
        void testParseWhatsappLowercase() {
            NotificationChannel channel = NotificationChannel.fromString("whatsapp");
            assertEquals(NotificationChannel.WHATSAPP, channel);
        }

        @Test
        @DisplayName("Should parse WHATSAPP in mixed case")
        void testParseWhatsappMixedCase() {
            NotificationChannel channel = NotificationChannel.fromString("Whatsapp");
            assertEquals(NotificationChannel.WHATSAPP, channel);
        }

        @Test
        @DisplayName("Should parse WHATSAPP with alternating case")
        void testParseWhatsappAlternatingCase() {
            NotificationChannel channel = NotificationChannel.fromString("WhAtSaPp");
            assertEquals(NotificationChannel.WHATSAPP, channel);
        }
    }

    @Nested
    @DisplayName("fromString Invalid Input Tests")
    class FromStringInvalidInputTests {

        @Test
        @DisplayName("Should throw IllegalArgumentException for invalid channel name")
        void testInvalidChannelName() {
            assertThrows(IllegalArgumentException.class, () -> {
                NotificationChannel.fromString("MAIL");
            });
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException for typo in channel name")
        void testChannelNameWithTypo() {
            assertThrows(IllegalArgumentException.class, () -> {
                NotificationChannel.fromString("EMIAL");
            });
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException for completely wrong channel name")
        void testCompletelyWrongChannelName() {
            assertThrows(IllegalArgumentException.class, () -> {
                NotificationChannel.fromString("TELEGRAM");
            });
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException for empty string")
        void testEmptyString() {
            assertThrows(IllegalArgumentException.class, () -> {
                NotificationChannel.fromString("");
            });
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException with descriptive message")
        void testExceptionMessageDescriptive() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                NotificationChannel.fromString("INVALID");
            });
            String message = exception.getMessage();
            assertTrue(message.contains("Invalid channel"), "Message should mention invalid channel");
            assertTrue(message.contains("INVALID"), "Message should contain the invalid value");
            assertTrue(message.contains("EMAIL") || message.contains("SMS") || message.contains("WHATSAPP"),
                "Message should suggest valid values");
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException with all valid values in message")
        void testExceptionMessageContainsAllValidValues() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                NotificationChannel.fromString("INVALID");
            });
            String message = exception.getMessage();
            assertTrue(message.contains("EMAIL"), "Message should contain EMAIL");
            assertTrue(message.contains("SMS"), "Message should contain SMS");
            assertTrue(message.contains("WHATSAPP"), "Message should contain WHATSAPP");
        }
    }

    @Nested
    @DisplayName("fromString Edge Cases")
    class FromStringEdgeCases {

        @Test
        @DisplayName("Should throw exception for string with spaces")
        void testStringWithSpaces() {
            assertThrows(IllegalArgumentException.class, () -> {
                NotificationChannel.fromString(" EMAIL ");
            });
        }

        @Test
        @DisplayName("Should throw exception for string with special characters")
        void testStringWithSpecialChars() {
            assertThrows(IllegalArgumentException.class, () -> {
                NotificationChannel.fromString("EMAIL@");
            });
        }

        @Test
        @DisplayName("Should throw exception for string with numbers")
        void testStringWithNumbers() {
            assertThrows(IllegalArgumentException.class, () -> {
                NotificationChannel.fromString("EMAIL123");
            });
        }

        @Test
        @DisplayName("Should throw exception for numeric string")
        void testNumericString() {
            assertThrows(IllegalArgumentException.class, () -> {
                NotificationChannel.fromString("123");
            });
        }
    }

    @Nested
    @DisplayName("Enum Value Tests")
    class EnumValueTests {

        @Test
        @DisplayName("Should have EMAIL enum value")
        void testEmailEnumExists() {
            assertNotNull(NotificationChannel.EMAIL);
            assertEquals("Email", NotificationChannel.EMAIL.getDisplayName());
        }

        @Test
        @DisplayName("Should have SMS enum value")
        void testSmsEnumExists() {
            assertNotNull(NotificationChannel.SMS);
            assertEquals("SMS", NotificationChannel.SMS.getDisplayName());
        }

        @Test
        @DisplayName("Should have WHATSAPP enum value")
        void testWhatsappEnumExists() {
            assertNotNull(NotificationChannel.WHATSAPP);
            assertEquals("WhatsApp", NotificationChannel.WHATSAPP.getDisplayName());
        }

        @Test
        @DisplayName("Should have exactly three enum values")
        void testEnumValuesCount() {
            NotificationChannel[] values = NotificationChannel.values();
            assertEquals(3, values.length, "Should have exactly 3 notification channels");
        }
    }

    @Nested
    @DisplayName("Display Name Tests")
    class DisplayNameTests {

        @Test
        @DisplayName("Should return correct display name for EMAIL")
        void testEmailDisplayName() {
            assertEquals("Email", NotificationChannel.EMAIL.getDisplayName());
        }

        @Test
        @DisplayName("Should return correct display name for SMS")
        void testSmsDisplayName() {
            assertEquals("SMS", NotificationChannel.SMS.getDisplayName());
        }

        @Test
        @DisplayName("Should return correct display name for WHATSAPP")
        void testWhatsappDisplayName() {
            assertEquals("WhatsApp", NotificationChannel.WHATSAPP.getDisplayName());
        }
    }

    @Nested
    @DisplayName("Round-trip Conversion Tests")
    class RoundTripConversionTests {

        @Test
        @DisplayName("Should convert EMAIL enum to string and back")
        void testEmailRoundTrip() {
            NotificationChannel original = NotificationChannel.EMAIL;
            String stringValue = original.name();
            NotificationChannel restored = NotificationChannel.fromString(stringValue);
            assertEquals(original, restored);
        }

        @Test
        @DisplayName("Should convert SMS enum to string and back")
        void testSmsRoundTrip() {
            NotificationChannel original = NotificationChannel.SMS;
            String stringValue = original.name();
            NotificationChannel restored = NotificationChannel.fromString(stringValue);
            assertEquals(original, restored);
        }

        @Test
        @DisplayName("Should convert WHATSAPP enum to string and back")
        void testWhatsappRoundTrip() {
            NotificationChannel original = NotificationChannel.WHATSAPP;
            String stringValue = original.name();
            NotificationChannel restored = NotificationChannel.fromString(stringValue);
            assertEquals(original, restored);
        }

        @Test
        @DisplayName("Should handle case-insensitive round-trip")
        void testCaseInsensitiveRoundTrip() {
            NotificationChannel original = NotificationChannel.EMAIL;
            NotificationChannel restored = NotificationChannel.fromString("email");
            assertEquals(original, restored);
        }
    }
}
