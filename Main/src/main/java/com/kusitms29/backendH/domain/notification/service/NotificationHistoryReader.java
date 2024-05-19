package com.kusitms29.backendH.domain.notification.service;

import com.kusitms29.backendH.domain.notification.entity.NotificationHistory;
import com.kusitms29.backendH.domain.notification.entity.TopCategory;
import com.kusitms29.backendH.domain.notification.repository.NotificationHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationHistoryReader {
    private final NotificationHistoryRepository notificationHistoryRepository;

    public List<NotificationHistory> findByTopCategoryAndUserId(TopCategory topCategory, Long userId) {
        return notificationHistoryRepository.findByTopCategoryAndUserId(topCategory, userId);
    }

}
