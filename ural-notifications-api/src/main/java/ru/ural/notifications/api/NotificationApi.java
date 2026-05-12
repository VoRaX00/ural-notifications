package ru.ural.notifications.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.ural.notifications.dto.NotificationDto;

@RequestMapping("/api/notifications")
@Tag(name = "Контроллер для работы с уведомлениями")
public interface NotificationApi {

    @GetMapping
    ResponseEntity<NotificationDto> getNotifications();

}
