package com.notifi.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NotificationRequestDTO {
    @NotBlank(message = "clientId is required")
    private String clientId;

    @NotBlank(message = "channel is required")
    private String channel;

    @NotBlank(message = "messageBody is required")
    private String messageBody;

    @NotBlank(message = "contentType is required")
    private String contentType;

    public NotificationRequestDTO() {
    }

    public NotificationRequestDTO(String clientId, String channel, String messageBody, String contentType) {
        this.clientId = clientId;
        this.channel = channel;
        this.messageBody = messageBody;
        this.contentType = contentType;
    }
}
