package com.kusitms29.backendH.domain.sync.entity;

import com.kusitms29.backendH.domain.user.entity.User;
import com.kusitms29.backendH.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "favoriteSync")
@Entity
public class FavoriteSync extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_sync_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sync_id")
    private Sync sync;
}
