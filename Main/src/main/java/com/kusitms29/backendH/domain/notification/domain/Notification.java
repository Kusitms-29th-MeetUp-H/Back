package com.kusitms29.backendH.domain.notification.domain;

import com.kusitms29.backendH.domain.user.domain.User;
import com.kusitms29.backendH.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "notification")
@Entity
public class Notification extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private Notification.NotificationType notificationType;

    public enum NotificationType {
        COMMENT, CHAT,
        CHAT_ROOM_NOTICE, SYNC_REMINDER, REVIEW
    }

    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;

    public enum Status {
        ACTIVE, INACTIVE;
    }

    public void setStatus(Status status) {
        this.status = Status.ACTIVE;
    }
}
