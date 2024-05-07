package com.kusitms29.backendH.domain.sync.domain;

import com.kusitms29.backendH.domain.category.domain.Category;
import com.kusitms29.backendH.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "sync_category")
@Entity
public class SyncCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sync_category_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sync_id")
    private Sync sync;
    //언어 : 언어 교환, 튜터링, 스터디
    //엔터테인먼트/예술 : 문화 예술, 영화, 드라마, 미술/디자인, 공연/전시, 음악
    //여행/동행 : 관광지, 자연, 휴양
    //액티비티 : 러닝/산책, 등산, 클라이밍, 자전거, 축구, 서핑, 테니스, 볼링, 탁구
    //푸드드링크 : 맛집, 카페, 술
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
}
