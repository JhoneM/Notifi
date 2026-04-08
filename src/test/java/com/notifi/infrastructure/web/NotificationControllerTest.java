package com.notifi.infrastructure.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notifi.infrastructure.web.dto.NotificationRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
@DisplayName("NotificationController Tests")
class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // ============ Happy Path Tests ============

    @Test
    @DisplayName("Should send EMAIL with PLAIN_TEXT and return 200 OK with status SENT")
    void testSendEmailPlainText() throws Exception {
        NotificationRequestDTO request = new NotificationRequestDTO(
            "user@example.com",
            "EMAIL",
            "Hello World",
            "PLAIN_TEXT"
        );

        mockMvc.perform(post("/api/notifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("SENT"))
            .andExpect(jsonPath("$.description").value("Notification successfully sent"));
    }

    @Test
    @DisplayName("Should send EMAIL with HTML and return 200 OK with status SENT")
    void testSendEmailHtml() throws Exception {
        NotificationRequestDTO request = new NotificationRequestDTO(
            "admin@example.com",
            "EMAIL",
            "<h1>Hello</h1>",
            "HTML"
        );

        mockMvc.perform(post("/api/notifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("SENT"));
    }

    @Test
    @DisplayName("Should send SMS with PLAIN_TEXT and return 200 OK with status SENT")
    void testSendSmsPlainText() throws Exception {
        NotificationRequestDTO request = new NotificationRequestDTO(
            "+1234567890",
            "SMS",
            "Test SMS message",
            "PLAIN_TEXT"
        );

        mockMvc.perform(post("/api/notifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("SENT"));
    }

    @Test
    @DisplayName("Should send WHATSAPP with PLAIN_TEXT and return 200 OK with status SENT")
    void testSendWhatsappPlainText() throws Exception {
        NotificationRequestDTO request = new NotificationRequestDTO(
            "+9876543210",
            "WHATSAPP",
            "WhatsApp message",
            "PLAIN_TEXT"
        );

        mockMvc.perform(post("/api/notifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("SENT"));
    }

    // ============ Validation Error Tests (400 Bad Request) ============

    @Test
    @DisplayName("Should return 400 when clientId is missing")
    void testMissingClientId() throws Exception {
        String jsonRequest = """
            {
                "channel": "EMAIL",
                "messageBody": "Test message",
                "contentType": "PLAIN_TEXT"
            }
            """;

        mockMvc.perform(post("/api/notifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonRequest))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").value("Bad Request"))
            .andExpect(jsonPath("$.message", containsString("clientId is required")));
    }

    @Test
    @DisplayName("Should return 400 when channel is missing")
    void testMissingChannel() throws Exception {
        String jsonRequest = """
            {
                "clientId": "user@example.com",
                "messageBody": "Test message",
                "contentType": "PLAIN_TEXT"
            }
            """;

        mockMvc.perform(post("/api/notifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonRequest))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").value("Bad Request"))
            .andExpect(jsonPath("$.message", containsString("channel is required")));
    }

    @Test
    @DisplayName("Should return 400 when messageBody is missing")
    void testMissingMessageBody() throws Exception {
        String jsonRequest = """
            {
                "clientId": "user@example.com",
                "channel": "EMAIL",
                "contentType": "PLAIN_TEXT"
            }
            """;

        mockMvc.perform(post("/api/notifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonRequest))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").value("Bad Request"))
            .andExpect(jsonPath("$.message", containsString("messageBody is required")));
    }

    @Test
    @DisplayName("Should return 400 when contentType is missing")
    void testMissingContentType() throws Exception {
        String jsonRequest = """
            {
                "clientId": "user@example.com",
                "channel": "EMAIL",
                "messageBody": "Test message"
            }
            """;

        mockMvc.perform(post("/api/notifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonRequest))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").value("Bad Request"))
            .andExpect(jsonPath("$.message", containsString("contentType is required")));
    }

    @Test
    @DisplayName("Should return 400 when channel has invalid value")
    void testInvalidChannel() throws Exception {
        NotificationRequestDTO request = new NotificationRequestDTO(
            "user@example.com",
            "MAIL",
            "Test message",
            "PLAIN_TEXT"
        );

        mockMvc.perform(post("/api/notifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").value("Bad Request"))
            .andExpect(jsonPath("$.message", containsString("Invalid channel")));
    }

    @Test
    @DisplayName("Should return 400 when contentType has invalid value")
    void testInvalidContentType() throws Exception {
        NotificationRequestDTO request = new NotificationRequestDTO(
            "user@example.com",
            "EMAIL",
            "Test message",
            "WORD"
        );

        mockMvc.perform(post("/api/notifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").value("Bad Request"))
            .andExpect(jsonPath("$.message", containsString("Invalid contentType")));
    }

    @Test
    @DisplayName("Should return 400 when clientId format is invalid for EMAIL channel")
    void testInvalidEmailFormat() throws Exception {
        NotificationRequestDTO request = new NotificationRequestDTO(
            "notanemail",
            "EMAIL",
            "Test message",
            "PLAIN_TEXT"
        );

        mockMvc.perform(post("/api/notifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").value("Bad Request"))
            .andExpect(jsonPath("$.message", containsString("Invalid clientId for EMAIL channel")));
    }

    @Test
    @DisplayName("Should return 400 when clientId format is invalid for SMS channel")
    void testInvalidSmsFormat() throws Exception {
        NotificationRequestDTO request = new NotificationRequestDTO(
            "1234567890",
            "SMS",
            "Test message",
            "PLAIN_TEXT"
        );

        mockMvc.perform(post("/api/notifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").value("Bad Request"))
            .andExpect(jsonPath("$.message", containsString("Invalid clientId for SMS channel")));
    }

    @Test
    @DisplayName("Should return 400 when clientId format is invalid for WHATSAPP channel")
    void testInvalidWhatsappFormat() throws Exception {
        NotificationRequestDTO request = new NotificationRequestDTO(
            "9876543210",
            "WHATSAPP",
            "Test message",
            "PLAIN_TEXT"
        );

        mockMvc.perform(post("/api/notifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").value("Bad Request"))
            .andExpect(jsonPath("$.message", containsString("Invalid clientId for WHATSAPP channel")));
    }

    @Test
    @DisplayName("Should return 400 when unsupported/unregistered channel is used")
    void testUnsupportedChannel() throws Exception {
        NotificationRequestDTO request = new NotificationRequestDTO(
            "user@example.com",
            "TELEGRAM",
            "Test message",
            "PLAIN_TEXT"
        );

        mockMvc.perform(post("/api/notifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").value("Bad Request"));
    }


    // ============ Edge Cases ============

    @Test
    @DisplayName("Should handle case-insensitive channel values")
    void testCaseInsensitiveChannel() throws Exception {
        NotificationRequestDTO request = new NotificationRequestDTO(
            "user@example.com",
            "email",
            "Test message",
            "PLAIN_TEXT"
        );

        mockMvc.perform(post("/api/notifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("SENT"));
    }

    @Test
    @DisplayName("Should handle case-insensitive contentType values")
    void testCaseInsensitiveContentType() throws Exception {
        NotificationRequestDTO request = new NotificationRequestDTO(
            "user@example.com",
            "EMAIL",
            "Test message",
            "plain_text"
        );

        mockMvc.perform(post("/api/notifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("SENT"));
    }
}
