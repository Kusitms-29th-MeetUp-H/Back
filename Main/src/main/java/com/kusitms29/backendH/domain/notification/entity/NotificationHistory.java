package com.kusitms29.backendH.domain.notification.entity;

import com.kusitms29.backendH.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "notification_history")
@Entity
public class NotificationHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_history_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String title;

    private String body;

    private String receiverToken;

    private LocalDateTime sentAt;

    private NotificationType notificationType;

    private TopCategory topCategory;

    private String infoId;
    private String infoId2;

    public static NotificationHistory createHistory(User user, String title, String body,
                                                                String receiverToken, LocalDateTime sentAt,
                                                                NotificationType notificationType, TopCategory topCategory,
                                                                String infoId, String infoId2) {
        return NotificationHistory.builder()
                .user(user)
                .title(title)
                .body(body)
                .receiverToken(receiverToken)
                .sentAt(sentAt)
                .notificationType(notificationType)
                .topCategory(topCategory)
                .infoId(infoId)
                .infoId2(infoId2)
                .build();
    }
}
