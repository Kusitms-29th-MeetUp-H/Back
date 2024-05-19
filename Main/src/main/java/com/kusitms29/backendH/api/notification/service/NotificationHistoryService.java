package com.kusitms29.backendH.api.notification.service;

import com.kusitms29.backendH.api.notification.service.dto.NotificationHistoryResponseDto;
import com.kusitms29.backendH.domain.notification.entity.NotificationHistory;
import com.kusitms29.backendH.domain.notification.entity.TopCategory;
import com.kusitms29.backendH.domain.notification.service.NotificationHistoryReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationHistoryService {

    private final NotificationHistoryReader notificationHistoryReader;

    public List<NotificationHistoryResponseDto> getNotificationByTopCategory(Long userId, String topCategory) {
        TopCategory enumTopCategory = TopCategory.getEnumTopCategoryFromStringTopCategory(topCategory);
        List<NotificationHistory> notificationHistories = notificationHistoryReader.findByTopCategoryAndUserId(enumTopCategory, userId);

        notificationHistories.sort(Comparator.comparing(NotificationHistory :: getSentAt).reversed());

        return notificationHistories.stream()
                .map(notificationHistory -> mapToNotificationHistoryResponseDto(notificationHistory, userId))
                .collect(Collectors.toList());
    }

    private NotificationHistoryResponseDto mapToNotificationHistoryResponseDto(NotificationHistory notificationHistory, Long userId) {
        return NotificationHistoryResponseDto.of(
                notificationHistory.getTitle(),
                notificationHistory.getBody(),
                "detail 내용들",
                notificationHistory.getSentAt()
        );
    }




}
