package com.kusitms29.backendH.domain.notification.repository;

import com.kusitms29.backendH.domain.notification.domain.Notification;
import com.kusitms29.backendH.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Optional<Notification> findByUserAndNotificationType(User user, Notification.NotificationType type);
}
