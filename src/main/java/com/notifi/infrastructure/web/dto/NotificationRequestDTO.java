package com.notifi.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;

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

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
