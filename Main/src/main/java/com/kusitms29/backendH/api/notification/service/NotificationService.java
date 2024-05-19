package com.kusitms29.backendH.api.notification.service;

import com.kusitms29.backendH.domain.notification.entity.NotificationSetting;
import com.kusitms29.backendH.domain.notification.entity.NotificationType;
import com.kusitms29.backendH.domain.notification.repository.NotificationRepository;
import com.kusitms29.backendH.domain.user.entity.User;
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
        for(NotificationType type : NotificationType.values()) {
            NotificationSetting notificationSetting = NotificationSetting.builder()
                    .user(user)
                    .notificationType(type)
                    .build();
            notificationRepository.save(notificationSetting);
        }
    }

    public void updateSettingActive(User user, NotificationType type) {
        NotificationSetting notificationSetting = notificationRepository.findByUserAndNotificationType(user, type)
                .orElseThrow(() -> new EntityNotFoundException(NOTIFICATION_NOT_FOUND));
        notificationSetting.setStatus(NotificationSetting.Status.ACTIVE);
        notificationRepository.save(notificationSetting);
    }

}
