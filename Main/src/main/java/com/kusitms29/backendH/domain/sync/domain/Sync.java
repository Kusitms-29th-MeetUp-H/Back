package com.kusitms29.backendH.domain.sync.domain;

import com.kusitms29.backendH.global.common.BaseEntity;
import com.kusitms29.backendH.domain.category.domain.Category;
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

    private String link;

    @Enumerated(EnumType.STRING)
    private SyncType syncType;

    //--Sync 상위/하위 카테고리--
    //언어, 엔터테인먼트/예술, 여행/동행, 액티비티, 푸드드링크, 기타(6)
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "parent_category_id")
    private Category parentCategory;

    //언어 : 언어 교환, 튜터링, 스터디
    //엔터테인먼트/예술 : 문화 예술, 영화, 드라마, 미술/디자인, 공연/전시, 음악
    //여행/동행 : 관광지, 자연, 휴양
    //액티비티 : 러닝/산책, 등산, 클라이밍, 자전거, 축구, 서핑, 테니스, 볼링, 탁구
    //푸드드링크 : 맛집, 카페, 술
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "child_category_id")
    private Category childCategory;
    //--

    private String name;
    private String image;
    private String content;
    private String location;
    private LocalDateTime date;

    //지속성 모임 : 모임 횟수
    @ColumnDefault("1")
    @Builder.Default()
    private int meeting_cnt = 1;
    @ColumnDefault("1")
    @Builder.Default()
    private int member_min = 1;
    @ColumnDefault("2")
    @Builder.Default()
    private int member_max = 2;
    @ColumnDefault("1")
    @Builder.Default()
    private int member_cnt = 1;
    @ColumnDefault("0")
    @Builder.Default()
    private int scrap_cnt = 0;
    @ColumnDefault("0")
    @Builder.Default()
    private int reported_cnt = 0;

    @Enumerated(EnumType.STRING)
    @Builder.Default()
    private SyncStatus syncStatus = SyncStatus.RECRUITING;

    public static Sync createSync(User user, String link, SyncType syncType,
                                   Category parentCategory, Category childCategory,
                                   String name, String image, String content, String location,
                                   LocalDateTime localDateTime,
                                   int meeting_cnt, int member_min, int member_max) {
        return Sync.builder()
                .user(user)
                .link(link)
                .syncType(syncType)
                .parentCategory(parentCategory)
                .childCategory(childCategory)
                .name(name)
                .image(image)
                .content(content)
                .location(location)
                .date(localDateTime)
                .meeting_cnt(meeting_cnt)
                .member_min(member_min)
                .member_max(member_max)
                .build();
    }

    public void updateMemberCnt(int member_cnt) {
        this.member_cnt = member_cnt;
    }
}
