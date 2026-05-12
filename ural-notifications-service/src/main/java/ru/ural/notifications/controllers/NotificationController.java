package ru.ural.notifications.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.ural.notifications.api.NotificationApi;
import ru.ural.notifications.dto.NotificationDto;
import ru.ural.notifications.services.NotificationService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class NotificationController implements NotificationApi {

    private final NotificationService notificationService;

    @Override
    public ResponseEntity<NotificationDto> getNotifications() {
        log.warn("Not implemented yet");
        return null;
    }

}
