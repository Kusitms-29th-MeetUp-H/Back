package com.kusitms29.backendH.domain.syncReview.domain;

import com.kusitms29.backendH.domain.BaseEntity;
import com.kusitms29.backendH.domain.sync.domain.Sync;
import com.kusitms29.backendH.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "syncReview")
@Entity
public class SyncReview extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sync_review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sync_id")
    private Sync sync;

    private String content;
}
