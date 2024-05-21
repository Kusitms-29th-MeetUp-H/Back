package com.kusitms29.backendH.api.notification.service;

import com.kusitms29.backendH.api.notification.service.dto.NotificationHistoryResponseDto;
import com.kusitms29.backendH.domain.comment.service.CommentReader;
import com.kusitms29.backendH.domain.notification.entity.NotificationHistory;
import com.kusitms29.backendH.domain.notification.entity.TopCategory;
import com.kusitms29.backendH.domain.notification.service.NotificationHistoryReader;
import com.kusitms29.backendH.global.error.exception.InvalidValueException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.kusitms29.backendH.global.error.ErrorCode.INVALID_NOTIFICATION_TYPE;

@Service
@RequiredArgsConstructor
public class NotificationHistoryService {

    private final NotificationHistoryReader notificationHistoryReader;
    private final CommentReader commentReader;

    public List<NotificationHistoryResponseDto> getNotificationByTopCategory(Long userId, String topCategory) {
        TopCategory enumTopCategory = TopCategory.getEnumTopCategoryFromStringTopCategory(topCategory);
        List<NotificationHistory> notificationHistories = notificationHistoryReader.findByTopCategoryAndUserId(enumTopCategory, userId);

        notificationHistories.sort(Comparator.comparing(NotificationHistory :: getSentAt).reversed());

        return notificationHistories.stream()
                .map(this::mapToNotificationHistoryResponseDto)
                .collect(Collectors.toList());
    }

    private NotificationHistoryResponseDto mapToNotificationHistoryResponseDto(NotificationHistory notificationHistory) {
        String detailContent = "";

        switch (notificationHistory.getNotificationType().name()) {
            case "CHAT":
                detailContent = notificationHistory.getInfoId2();
                break;
            case "CHAT_ROOM_NOTICE":
                detailContent = "지금 바로 채팅방에 입장해서 멤버들과 대화를 나눠보세요!";
                break;
            case "COMMENT":
                Long infoId2 = ((notificationHistory.getInfoId2() != null)&&(!notificationHistory.getInfoId2().isEmpty()) ? Long.parseLong(notificationHistory.getInfoId2()) : null);
                detailContent = commentReader.findById(infoId2).getContent();
                break;
            case "SYNC_REMINDER":
                detailContent = "즐거운 싱크되세요!";
                break;
            case "REVIEW":
                detailContent = "생생한 리뷰를 남겨보세요";
                break;
            default:
                throw new InvalidValueException(INVALID_NOTIFICATION_TYPE);
        }

        return NotificationHistoryResponseDto.of(
                notificationHistory.getInfoId(),
                notificationHistory.getTitle(),
                notificationHistory.getBody(),
                detailContent,
                notificationHistory.getSentAt()
        );
    }




}
