package ru.ural.notifications.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.ural.notifications.dto.NotificationRequest;
import ru.ural.notifications.services.ConsumerService;
import ru.ural.notifications.services.NotificationService;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationKafkaListener {

    private final ConsumerService consumerService;

    private final NotificationService notificationService;

    @KafkaListener(
            topics = "${kafka.notification-topic.name}",
            groupId = "${kafka.group-id}"
    )
    public void listen(String message) {
        log.info("Start listening message for send notification");

        NotificationRequest notificationRequest = consumerService.convertNotificationRequest(message);
        notificationService.saveAndSendNotification(notificationRequest);

        log.info("Finish listening message for send notification");
    }

}
