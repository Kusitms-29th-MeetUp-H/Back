package com.kusitms29.backendH.domain.sync.domain;

import com.kusitms29.backendH.domain.category.domain.Type;
import com.kusitms29.backendH.global.common.BaseEntity;
import com.kusitms29.backendH.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "sync")
@Entity
public class Sync extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sync_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;
    //모임장소개
    private String userIntro;
    //싱크소개
    private String syncIntro;
    private String link;

    @Enumerated(EnumType.STRING)
    private SyncType syncType;

    private String syncName;
    private String image;
    private String content;
    private String location;
    private LocalDateTime date;
    //지속성에서 정기모임
    private LocalDateTime routineDate;

    //지속성 모임 : 모임 횟수
    @ColumnDefault("1")
    @Builder.Default()
    private int member_min = 1;
    @ColumnDefault("2")
    @Builder.Default()
    private int member_max = 2;

    @Enumerated(EnumType.STRING)
    protected Sync.Status status;
    @Enumerated(EnumType.STRING)
    private Type type;
    private String detailType;

    public enum Status {
        RECRUITING, COMPLETED, DELETED;
    }

    public static Sync createSync(User user, String link, SyncType syncType,
                                   String name, String image, String content, String location,
                                   LocalDateTime localDateTime, int member_min, int member_max) {
        return Sync.builder()
                .user(user)
                .link(link)
                .syncType(syncType)
                .syncName(name)
                .image(image)
                .content(content)
                .location(location)
                .date(localDateTime)
                .member_min(member_min)
                .member_max(member_max)
                .build();
    }

}
