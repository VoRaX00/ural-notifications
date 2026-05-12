package ru.ural.notifications.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.ural.notifications.dto.NotificationRequest;
import ru.ural.notifications.entities.Notification;
import ru.ural.notifications.mappers.NotificationMapper;
import ru.ural.notifications.repositories.NotificationRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationSender notificationSender;

    private final NotificationRepository notificationRepository;

    private final NotificationMapper notificationMapper;

    public void saveAndSendNotification(NotificationRequest notificationRequest) {
        Notification notification = notificationMapper.toEntity(notificationRequest);
        Notification savedNotification = notificationRepository.save(notification);

        try {
            notificationSender.sendNotification(savedNotification);
            savedNotification.setIsSending(true);
        } catch (RuntimeException e) {
            log.error("Error while sending notification", e);
            savedNotification.setIsSending(false);
        } finally {
            notificationRepository.save(savedNotification);
        }
    }

}
