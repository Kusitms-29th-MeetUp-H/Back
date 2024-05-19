package com.kusitms29.backendH.infra.external.fcm.service;

import com.google.firebase.messaging.*;
import com.kusitms29.backendH.domain.notification.entity.NotificationHistory;
import com.kusitms29.backendH.domain.notification.entity.NotificationType;
import com.kusitms29.backendH.domain.notification.repository.NotificationHistoryRepository;
import com.kusitms29.backendH.domain.sync.entity.Sync;
import com.kusitms29.backendH.domain.sync.service.SyncReader;
import com.kusitms29.backendH.domain.user.entity.User;
import com.kusitms29.backendH.domain.user.service.UserReader;
import com.kusitms29.backendH.global.common.TimeCalculator;
import com.kusitms29.backendH.infra.external.fcm.MessageTemplate;
import com.kusitms29.backendH.domain.sync.repository.SyncRepository;
import com.kusitms29.backendH.infra.external.fcm.repository.FCMTokenRepository;
import com.kusitms29.backendH.infra.external.fcm.service.dto.NotificationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class PushNotificationService {
    private final FirebaseMessaging firebaseMessaging;
    private final FCMTokenRepository fcmTokenRepository;
    private final SyncRepository syncRepository;
    private final NotificationHistoryRepository notificationHistoryRepository;
    private final UserReader userReader;
    private final SyncReader syncReader;

    public void sendSyncReminder() {
        //오늘 자정
        LocalDateTime today = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        List<Map<String, Object>> hurrySyncInfo = syncRepository.findHurrySyncInfo(today);
        log.info("hurrySyncInfo.size() :: " + hurrySyncInfo.size());
        for (Map<String, Object> userInfo : hurrySyncInfo) {
            NotificationDto dto = new NotificationDto(
                    userInfo.get("user_id").toString(),
                    (String) userInfo.get("user_name"),
                    (String) userInfo.get("sync_name"),
                    MessageTemplate.SYNC_REMINDER);
            sendMessage(dto);

            //sync_type : 지속성일 때, 모임날짜가 지났다면 다음 요일 날짜로 업데이트
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime current_routine_date = LocalDateTime.parse(userInfo.get("effective_date").toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));

            if(userInfo.get("sync_type").toString().equals("LONGTIME") && current_routine_date.isBefore(now)) {
                String regularDay = userInfo.get("regular_day").toString();

                String regularTimeString = userInfo.get("regular_time").toString();
                LocalTime regularTime = LocalTime.parse(regularTimeString, DateTimeFormatter.ofPattern("HH:mm:ss"));

                LocalDateTime nextDate = TimeCalculator.getNextWeekDate(TimeCalculator.convertStringToDayOfWeek(regularDay));
                nextDate = nextDate.with(regularTime);

                Sync sync = syncReader.findById(Long.parseLong(userInfo.get("sync_id").toString()));
                sync.updateNextDate(nextDate);
            }

            //알림 기록
            User alarmedUser = userReader.findByUserId(Long.parseLong(dto.getId()));
            NotificationHistory history = NotificationHistory.createHistory(
                    alarmedUser,
                    dto.getTemplate().getTitle(),
                    createMessageBody(dto),
                    getToken(dto.getId()),
                    now,
                    NotificationType.SYNC_REMINDER
            );
            notificationHistoryRepository.save(history);
        }
        log.info("Sync reminders sent successfully.");
    }

    private void sendMessage(NotificationDto dto) {
        //FCM 토큰 확인
        if (!hasKey(dto.getId())) {
            log.warn("FCM token not found for user with ID:" + dto.getId());
            return;
        }

        //메세지 보내기
        try{
            firebaseMessaging.send(createMessage(dto));
        } catch (FirebaseMessagingException e) {
            if(e.getMessagingErrorCode() == MessagingErrorCode.UNREGISTERED) {
                log.error("FCM token for user {} is invalid or unregistered", dto.getId());
                deleteToken(dto.getId());
            } else {
                log.error("Failed to send FCM message to user {}", dto.getId());
            }
        }
    }

    private Message createMessage(NotificationDto dto) {
        return Message.builder()
                .setToken(getToken(dto.getId()))
                .setNotification(createNotification(dto))
                .build();
    }

    private Notification createNotification(NotificationDto dto) {
        return Notification.builder()
                .setTitle(dto.getTemplate().getTitle())
                .setBody(createMessageBody(dto))
                .build();
    }

    private String createMessageBody(NotificationDto dto) {
        if(!dto.getStr2().isEmpty()) {
            return String.format(dto.getTemplate().getContent(), dto.getStr1(), dto.getStr2());
        }
        if(!dto.getStr1().isEmpty()) {
            return String.format(dto.getTemplate().getContent(), dto.getStr1());
        }
        return dto.getTemplate().getContent();
    }

    public void saveToken(String id, String fcmToken) { fcmTokenRepository.saveToken(id, fcmToken); }

    public void deleteToken(String id) {
        fcmTokenRepository.deleteToken(id);
    }

    private String getToken(String id) {
        return fcmTokenRepository.getToken(id);
    }

    private boolean hasKey(String id) {
        return fcmTokenRepository.hasKey(id);
    }
}
