package com.notifi.infrastructure.web;

import com.notifi.application.NotificationRequest;
import com.notifi.application.NotificationService;
import com.notifi.domain.channel.NotificationChannel;
import com.notifi.domain.model.ContentType;
import com.notifi.domain.model.NotificationStatus;
import com.notifi.infrastructure.web.dto.NotificationRequestDTO;
import com.notifi.infrastructure.web.dto.NotificationResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/notifications")
    public ResponseEntity<NotificationResponseDTO> sendNotification(@Valid @RequestBody NotificationRequestDTO dto) {
        NotificationChannel channel = NotificationChannel.fromString(dto.getChannel());
        ContentType contentType = ContentType.fromString(dto.getContentType());

        NotificationRequest request = new NotificationRequest(
            dto.getClientId(),
            channel,
            dto.getMessageBody(),
            contentType
        );

        NotificationStatus status = notificationService.sendNotification(request);
        return ResponseEntity.ok(new NotificationResponseDTO(status.name(), status.getDescription()));
    }

}
