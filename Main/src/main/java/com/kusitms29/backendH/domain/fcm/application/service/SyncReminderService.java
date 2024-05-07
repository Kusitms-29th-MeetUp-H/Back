package com.kusitms29.backendH.domain.fcm.application.service;

import com.google.firebase.messaging.*;
import com.kusitms29.backendH.domain.fcm.application.controller.dto.SyncReminderDto;
import com.kusitms29.backendH.domain.fcm.MessageTemplate;
import com.kusitms29.backendH.domain.sync.repository.SyncRepository;
import com.kusitms29.backendH.domain.fcm.repository.FCMTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class SyncReminderService {
    private final FirebaseMessaging firebaseMessaging;
    private final FCMTokenRepository fcmTokenRepository;
    private final SyncRepository syncRepository;

    public void sendSyncReminder() {
        //오늘 자정
        LocalDateTime today = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        List<Map<String, Object>> hurrySyncInfo = syncRepository.findHurrySyncInfo(today);

        for (Map<String, Object> userInfo : hurrySyncInfo) {
            sendMessage(new SyncReminderDto(
                    userInfo.get("user_id").toString(),
                    (String) userInfo.get("user_name"),
                    (String) userInfo.get("sync_name"),
                    MessageTemplate.SYNC_REMINDER
            ));
        }
        log.info("Sync reminders sent successfully.");
    }

    private void sendMessage(SyncReminderDto dto) {
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

    private Message createMessage(SyncReminderDto dto) {
        return Message.builder()
                .setToken(getToken(dto.getId()))
                .setNotification(createNotification(dto))
                .build();
    }

    private Notification createNotification(SyncReminderDto dto) {
        return Notification.builder()
                .setTitle(dto.getTemplate().getTitle())
                .setBody(createMessageBody(dto))
                .build();
    }

    private String createMessageBody(SyncReminderDto dto) {
        return String.format(dto.getTemplate().getContent(), dto.getName());
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
