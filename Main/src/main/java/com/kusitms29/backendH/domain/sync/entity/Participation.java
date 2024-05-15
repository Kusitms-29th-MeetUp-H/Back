package com.kusitms29.backendH.domain.sync.entity;

import com.kusitms29.backendH.global.common.BaseEntity;
import com.kusitms29.backendH.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "participation")
@Entity
public class Participation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sync_id")
    private Sync sync;

    public static Participation createParticipation(User user, Sync sync) {
        return Participation.builder()
                .user(user)
                .sync(sync)
                .build();
    }
}
