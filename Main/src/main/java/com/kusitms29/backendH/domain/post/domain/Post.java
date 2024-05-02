package com.kusitms29.backendH.domain.post.domain;

import com.kusitms29.backendH.domain.BaseEntity;
import com.kusitms29.backendH.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "post")
@Entity
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String content;
    @ColumnDefault("0")
    private int like_cnt;
    @ColumnDefault("0")
    private int comment_cnt;
    @ColumnDefault("0")
    private int view_cnt;
}
