package ru.ural.notifications.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.ural.notifications.dto.NotificationDto;
import ru.ural.notifications.dto.NotificationRequest;
import ru.ural.notifications.entities.Notification;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NotificationMapper {

    @Mapping(target = "isSending", ignore = true)
    Notification toEntity(NotificationRequest notificationRequest);

    NotificationDto toDto(Notification notification);

}
