package com.kusitms29.backendH.domain.sync.entity;

import com.kusitms29.backendH.global.common.BaseEntity;
import com.kusitms29.backendH.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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

    public SyncReview(User user, Sync sync, String content) {
        this.user = user;
        this.sync = sync;
        this.content = content;
    }

    public static SyncReview createReview(User user, Sync sync, String content){
        return new SyncReview(user, sync, content);
    }

}
