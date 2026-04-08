package com.notifi.domain.client;

import com.notifi.domain.channel.NotificationChannel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ClientReference Tests")
class ClientReferenceTest {

    @Nested
    @DisplayName("Email Validation Tests")
    class EmailValidationTests {

        @Test
        @DisplayName("Should accept valid email format")
        void testValidEmailFormat() {
            assertDoesNotThrow(() -> {
                new ClientReference("user@example.com", NotificationChannel.EMAIL);
            });
        }

        @Test
        @DisplayName("Should accept email with multiple dots in domain")
        void testValidEmailWithMultipleDots() {
            assertDoesNotThrow(() -> {
                new ClientReference("user@mail.example.co.uk", NotificationChannel.EMAIL);
            });
        }

        @Test
        @DisplayName("Should accept email with numbers and special characters")
        void testValidEmailWithSpecialChars() {
            assertDoesNotThrow(() -> {
                new ClientReference("user.name+tag@example.com", NotificationChannel.EMAIL);
            });
        }

        @Test
        @DisplayName("Should reject invalid email without @ symbol")
        void testInvalidEmailMissingAt() {
            assertThrows(IllegalArgumentException.class, () -> {
                new ClientReference("userexample.com", NotificationChannel.EMAIL);
            }, "Should throw exception for email without @");
        }

        @Test
        @DisplayName("Should reject invalid email without domain extension")
        void testInvalidEmailMissingDomain() {
            assertThrows(IllegalArgumentException.class, () -> {
                new ClientReference("user@example", NotificationChannel.EMAIL);
            }, "Should throw exception for email without extension");
        }

        @Test
        @DisplayName("Should reject invalid email with multiple @ symbols")
        void testInvalidEmailMultipleAt() {
            assertThrows(IllegalArgumentException.class, () -> {
                new ClientReference("user@@example.com", NotificationChannel.EMAIL);
            }, "Should throw exception for multiple @ symbols");
        }

        @Test
        @DisplayName("Should reject email with whitespace")
        void testInvalidEmailWithWhitespace() {
            assertThrows(IllegalArgumentException.class, () -> {
                new ClientReference("user @example.com", NotificationChannel.EMAIL);
            }, "Should throw exception for email with whitespace");
        }
    }

    @Nested
    @DisplayName("Phone Number Validation Tests (E.164 Format)")
    class PhoneNumberValidationTests {

        @Test
        @DisplayName("Should accept valid E.164 format for SMS")
        void testValidE164FormatSms() {
            assertDoesNotThrow(() -> {
                new ClientReference("+1234567890", NotificationChannel.SMS);
            });
        }

        @Test
        @DisplayName("Should accept valid E.164 format for WhatsApp")
        void testValidE164FormatWhatsapp() {
            assertDoesNotThrow(() -> {
                new ClientReference("+9876543210", NotificationChannel.WHATSAPP);
            });
        }

        @Test
        @DisplayName("Should accept E.164 with long country code")
        void testValidE164WithLongCountryCode() {
            assertDoesNotThrow(() -> {
                new ClientReference("+551140411234", NotificationChannel.SMS);
            });
        }

        @Test
        @DisplayName("Should reject phone number without plus prefix")
        void testInvalidPhoneWithoutPlus() {
            assertThrows(IllegalArgumentException.class, () -> {
                new ClientReference("1234567890", NotificationChannel.SMS);
            }, "Should throw exception for phone without +");
        }

        @Test
        @DisplayName("Should reject phone number with letters")
        void testInvalidPhoneWithLetters() {
            assertThrows(IllegalArgumentException.class, () -> {
                new ClientReference("+12345ABC90", NotificationChannel.SMS);
            }, "Should throw exception for phone with letters");
        }

        @Test
        @DisplayName("Should reject phone number with special characters")
        void testInvalidPhoneWithSpecialChars() {
            assertThrows(IllegalArgumentException.class, () -> {
                new ClientReference("+1234-567-890", NotificationChannel.SMS);
            }, "Should throw exception for phone with dashes");
        }

        @Test
        @DisplayName("Should reject phone number with spaces")
        void testInvalidPhoneWithSpaces() {
            assertThrows(IllegalArgumentException.class, () -> {
                new ClientReference("+1234 567 890", NotificationChannel.SMS);
            }, "Should throw exception for phone with spaces");
        }

        @Test
        @DisplayName("Should reject SMS when using email format")
        void testInvalidSmsWithEmailFormat() {
            assertThrows(IllegalArgumentException.class, () -> {
                new ClientReference("user@example.com", NotificationChannel.SMS);
            }, "Should throw exception when email is used with SMS");
        }

        @Test
        @DisplayName("Should reject WhatsApp when using email format")
        void testInvalidWhatsappWithEmailFormat() {
            assertThrows(IllegalArgumentException.class, () -> {
                new ClientReference("user@example.com", NotificationChannel.WHATSAPP);
            }, "Should throw exception when email is used with WhatsApp");
        }
    }

    @Nested
    @DisplayName("Null Handling Tests")
    class NullHandlingTests {

        @Test
        @DisplayName("Should throw NullPointerException when clientId is null")
        void testNullClientId() {
            assertThrows(NullPointerException.class, () -> {
                new ClientReference(null, NotificationChannel.EMAIL);
            }, "Should throw NullPointerException for null clientId");
        }

        @Test
        @DisplayName("Should throw NullPointerException when channel is null")
        void testNullChannel() {
            assertThrows(NullPointerException.class, () -> {
                new ClientReference("user@example.com", null);
            }, "Should throw NullPointerException for null channel");
        }

        @Test
        @DisplayName("Should throw NullPointerException when both parameters are null")
        void testBothParametersNull() {
            assertThrows(NullPointerException.class, () -> {
                new ClientReference(null, null);
            }, "Should throw NullPointerException when both are null");
        }
    }

    @Nested
    @DisplayName("Accessor Tests")
    class AccessorTests {

        @Test
        @DisplayName("Should return clientId correctly for email")
        void testGetClientIdEmail() {
            String email = "user@example.com";
            ClientReference reference = new ClientReference(email, NotificationChannel.EMAIL);
            assertEquals(email, reference.getClientId());
        }

        @Test
        @DisplayName("Should return clientId correctly for phone number")
        void testGetClientIdPhone() {
            String phone = "+1234567890";
            ClientReference reference = new ClientReference(phone, NotificationChannel.SMS);
            assertEquals(phone, reference.getClientId());
        }

        @Test
        @DisplayName("Should return channel correctly")
        void testGetChannel() {
            ClientReference reference = new ClientReference("user@example.com", NotificationChannel.EMAIL);
            assertEquals(NotificationChannel.EMAIL, reference.getPreferredChannel());
        }

        @Test
        @DisplayName("Should return correct channel for SMS")
        void testGetChannelSms() {
            ClientReference reference = new ClientReference("+1234567890", NotificationChannel.SMS);
            assertEquals(NotificationChannel.SMS, reference.getPreferredChannel());
        }

        @Test
        @DisplayName("Should return correct channel for WhatsApp")
        void testGetChannelWhatsapp() {
            ClientReference reference = new ClientReference("+9876543210", NotificationChannel.WHATSAPP);
            assertEquals(NotificationChannel.WHATSAPP, reference.getPreferredChannel());
        }
    }

    @Nested
    @DisplayName("Edge Case Tests")
    class EdgeCaseTests {

        @Test
        @DisplayName("Should accept email with numeric local part")
        void testEmailWithNumericLocalPart() {
            assertDoesNotThrow(() -> {
                new ClientReference("123456@example.com", NotificationChannel.EMAIL);
            });
        }

        @Test
        @DisplayName("Should accept email with hyphen in domain")
        void testEmailWithHyphenInDomain() {
            assertDoesNotThrow(() -> {
                new ClientReference("user@ex-ample.com", NotificationChannel.EMAIL);
            });
        }

        @Test
        @DisplayName("Should accept single digit phone numbers (unlikely but technically valid E.164)")
        void testSingleDigitPhone() {
            assertDoesNotThrow(() -> {
                new ClientReference("+1", NotificationChannel.SMS);
            });
        }

        @Test
        @DisplayName("Should accept very long phone numbers (E.164 allows up to 15 digits)")
        void testVeryLongPhoneNumber() {
            assertDoesNotThrow(() -> {
                new ClientReference("+123456789012345", NotificationChannel.SMS);
            });
        }
    }

    @Nested
    @DisplayName("Error Message Tests")
    class ErrorMessageTests {

        @Test
        @DisplayName("Should provide helpful error message for invalid email")
        void testEmailErrorMessage() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                new ClientReference("invalid-email", NotificationChannel.EMAIL);
            });
            assertTrue(exception.getMessage().contains("Invalid clientId for EMAIL channel"),
                "Error message should specify EMAIL channel");
            assertTrue(exception.getMessage().contains("user@domain.com"),
                "Error message should suggest correct format");
        }

        @Test
        @DisplayName("Should provide helpful error message for invalid SMS phone")
        void testSmsErrorMessage() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                new ClientReference("1234567890", NotificationChannel.SMS);
            });
            assertTrue(exception.getMessage().contains("Invalid clientId for SMS channel"),
                "Error message should specify SMS channel");
            assertTrue(exception.getMessage().contains("+<country_code><number>"),
                "Error message should suggest E.164 format");
        }

        @Test
        @DisplayName("Should provide helpful error message for invalid WhatsApp phone")
        void testWhatsappErrorMessage() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                new ClientReference("9876543210", NotificationChannel.WHATSAPP);
            });
            assertTrue(exception.getMessage().contains("Invalid clientId for WHATSAPP channel"),
                "Error message should specify WHATSAPP channel");
        }
    }
}
