package com.kusitms29.backendH.domain.notification.repository;

import com.kusitms29.backendH.domain.notification.entity.NotificationSetting;
import com.kusitms29.backendH.domain.notification.entity.NotificationType;
import com.kusitms29.backendH.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationRepository extends JpaRepository<NotificationSetting, Long> {
    Optional<NotificationSetting> findByUserAndNotificationType(User user, NotificationType type);
}
