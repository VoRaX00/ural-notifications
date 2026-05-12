package ru.ural.notifications.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.ural.notifications.dto.email.EmailNotificationRequest;
import ru.ural.notifications.dto.contract.NotificationContractRequest;
import ru.ural.notifications.services.ConsumerService;
import ru.ural.notifications.services.NotificationService;

import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationKafkaListener {

    private static final String HEADER_NAME_CORRELATION_ID = "correlationId";

    private final ConsumerService consumerService;

    private final NotificationService notificationService;

    @KafkaListener(
            topics = "${kafka.notification-email-topic.name}",
            groupId = "${kafka.group-id}"
    )
    public void listen(ConsumerRecord<String, String> record) {
        String correlationId = getHeaderValue(record, HEADER_NAME_CORRELATION_ID);
        log.info("Start listening message for send notification, correlationId: {}", correlationId);

        String message = record.value();
        EmailNotificationRequest emailNotificationRequest = consumerService.convertNotificationRequest(message);
        notificationService.saveAndSendEmailNotification(emailNotificationRequest);

        log.info("Finish listening message for send notification, correlationId: {}", correlationId);
    }

    @KafkaListener(
            topics = "${kafka.notification-contract-topic.name}",
            groupId = "${kafka.group-id}"
    )
    public void listenContract(ConsumerRecord<String, String> record) {
        String correlationId = getHeaderValue(record, HEADER_NAME_CORRELATION_ID);
        log.info("Start listening message for send contract notification, correlationId: {}", correlationId);

        String message = record.value();
        NotificationContractRequest notificationRequest = consumerService.convertNotificationContractRequest(message);
        notificationService.createNotification(notificationRequest);

        log.info("Finish listening message for send contract notification, correlationId: {}", correlationId);
    }

    private String getHeaderValue(ConsumerRecord<String, String> record, String headerName) {
        Header header = record.headers().lastHeader(headerName);
        if (header == null) {
            return null;
        }

        return new String(header.value(), StandardCharsets.UTF_8);
    }

}
