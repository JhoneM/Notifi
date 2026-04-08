package com.notifi.infrastructure.web.dto;

import lombok.Data;

@Data
public class NotificationResponseDTO {
    private String status;
    private String description;

    public NotificationResponseDTO() {
    }

    public NotificationResponseDTO(String status, String description) {
        this.status = status;
        this.description = description;
    }
}
