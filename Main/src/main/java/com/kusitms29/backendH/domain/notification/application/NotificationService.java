package com.kusitms29.backendH.domain.notification.application;

import com.kusitms29.backendH.domain.notification.domain.Notification;
import com.kusitms29.backendH.domain.notification.repository.NotificationRepository;
import com.kusitms29.backendH.domain.user.domain.User;
import com.kusitms29.backendH.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.kusitms29.backendH.global.error.ErrorCode.NOTIFICATION_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    @Transactional
    public void createNotificationSetting(User user) {
        for(Notification.NotificationType type : Notification.NotificationType.values()) {
            Notification notification = Notification.builder()
                    .user(user)
                    .notificationType(type)
                    .build();
            notificationRepository.save(notification);
        }
    }

    public void updateSettingActive(User user, Notification.NotificationType type) {
        Notification notification = notificationRepository.findByUserAndNotificationType(user, type)
                .orElseThrow(() -> new EntityNotFoundException(NOTIFICATION_NOT_FOUND));
        notification.setStatus(Notification.Status.ACTIVE);
        notificationRepository.save(notification);
    }

}
