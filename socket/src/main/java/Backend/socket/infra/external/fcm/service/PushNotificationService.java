package Backend.socket.infra.external.fcm.service;

import Backend.socket.domain.chat.application.controller.dto.response.ChatMessageElementResponseDto;
import Backend.socket.domain.chat.domain.Room;
import Backend.socket.domain.chat.domain.User;
import Backend.socket.domain.chat.domain.notification.entity.NotificationHistory;
import Backend.socket.domain.chat.domain.notification.entity.NotificationType;
import Backend.socket.domain.chat.domain.notification.entity.TopCategory;
import Backend.socket.domain.chat.domain.notification.repository.NotificationHistoryRepository;
import Backend.socket.domain.chat.repository.RoomRepository;
import Backend.socket.domain.chat.repository.UserRepository;
import Backend.socket.infra.external.fcm.MessageTemplate;
import Backend.socket.infra.external.fcm.repository.FCMTokenRepository;
import Backend.socket.infra.external.fcm.service.dto.NotificationDto;
import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class PushNotificationService {
    private final FirebaseMessaging firebaseMessaging;
    private final FCMTokenRepository fcmTokenRepository;

    private final UserRepository userRepository;
    private final NotificationHistoryRepository notificationHistoryRepository;

    @Transactional
    public void sendChatMessageNotification(Room room, ChatMessageElementResponseDto chatMessage,
                                            List<String> sessionIdList) {
        for(String sessionId : sessionIdList) {
            //단톡방에 있는 사람들
            User user = userRepository.findBySessionId(sessionId).orElseThrow();
            if(user.getSessionId().equals(chatMessage.getSessionId())) { //자기 자신 제외 알림.
                continue;
            }
            User fromUser = userRepository.findBySessionId(chatMessage.getSessionId()).orElseThrow();

            NotificationDto dto = NotificationDto.getChatMessageAlarm(
                    user.getId(), //받는이
                    room.getRoomName(), //채팅방 이름
                    fromUser.getId(), //보내는 이
                    MessageTemplate.CHAT, //채팅 템플릿
                    room.getRoomSession(), //방 세션 정보
                    chatMessage.getContent() //채팅 내용
            );
            sendMessage(dto);

            //알림 기록
            LocalDateTime now = LocalDateTime.now();
            NotificationHistory history = NotificationHistory.createHistory(
                    user,
                    dto.getTemplate().getTitle(),
                    createMessageBody(dto),
                    getToken(dto.getId()),
                    now,
                    NotificationType.CHAT,
                    TopCategory.ACTIVITY,
                    room.getRoomName(),
                    ""
            );
            notificationHistoryRepository.save(history);

            log.info("chatMessage notification sent to {} successfully. ", user.getUserName());
        }

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
        AndroidConfig androidConfig = AndroidConfig.builder()
                .setNotification(AndroidNotification.builder()
                        .setChannelId(dto.getChannelId())
                        .setTitle(dto.getTemplate().getTitle())
                        .setBody(createMessageBody(dto))
                        .build())
                .build();

        return Message.builder()
                .setToken(getToken(dto.getId()))
                .setAndroidConfig(androidConfig)
                .build();
    }

    private String createMessageBody(NotificationDto dto) {
        if(dto.getStr2() != null && !dto.getStr2().isEmpty()) {
            return String.format(dto.getTemplate().getContent(), dto.getStr1(), dto.getStr2());
        }
        if(dto.getStr1() != null && !dto.getStr1().isEmpty()) {
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