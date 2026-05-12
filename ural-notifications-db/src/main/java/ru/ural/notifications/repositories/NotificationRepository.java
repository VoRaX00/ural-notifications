package ru.ural.notifications.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ural.notifications.entities.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
