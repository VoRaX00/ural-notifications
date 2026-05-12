package ru.ural.notifications.services;

import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ural.exceptions.InternalServerException;
import ru.ural.notifications.dto.NotificationRequest;

@Service
@RequiredArgsConstructor
public class ConsumerService {

    private final JsonMapper jsonMapper;

    public NotificationRequest convertNotificationRequest(String message) {
        return convertRequest(message, NotificationRequest.class);
    }

    private <T> T convertRequest(String message, Class<T> clazz) {
        try {
            return jsonMapper.readValue(message, clazz);
        } catch (Exception e) {
            throw new InternalServerException(String.format("Invalid mapping request with message: %s", message), e);
        }
    }

}
