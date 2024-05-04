package com.kusitms29.backendH.domain.sync.domain;

import com.kusitms29.backendH.domain.BaseEntity;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String link;

    @Enumerated(EnumType.STRING)
    private SyncType syncType;

    //--Sync 상위/하위 카테고리--
    //언어, 엔터테인먼트/예술, 여행/동행, 액티비티, 푸드드링크, 기타(6)
    @OneToOne
    @JoinColumn(name = "parent_category_id")
    private Category parentCategory;

    //언어 : 언어 교환, 튜터링, 스터디
    //엔터테인먼트/예술 : 문화 예술, 영화, 드라마, 미술/디자인, 공연/전시, 음악
    //여행/동행 : 관광지, 자연, 휴양
    //액티비티 : 러닝/산책, 등산, 클라이밍, 자전거, 축구, 서핑, 테니스, 볼링, 탁구
    //푸드드링크 : 맛집, 카페, 술
    @OneToOne
    @JoinColumn(name = "child_category_id")
    private Category childCategory;
    //--

    private String name;
    private String image;
    private String comment;
    private String location;
    private LocalDateTime date;

    //지속성 모임 : 모임 횟수
    @ColumnDefault("1")
    private int meeting_cnt;
    @ColumnDefault("1")
    private int member_min;
    @ColumnDefault("2")
    private int member_max;
    @ColumnDefault("1")
    private int member_cnt;
    @ColumnDefault("0")
    private int scrap_cnt;
    @ColumnDefault("0")
    private int reported_cnt;

    @Enumerated(EnumType.STRING)
    private SyncStatus syncStatus = SyncStatus.RECRUITING;
}
