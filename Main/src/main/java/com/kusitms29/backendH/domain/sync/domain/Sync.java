package com.kusitms29.backendH.domain.sync.domain;

import com.kusitms29.backendH.domain.BaseEntity;
import com.kusitms29.backendH.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

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
    @JoinColumn(name = "owner")
    private User owner;

    private String link;
    @Enumerated(EnumType.STRING)
    private Category category;
    @Enumerated(EnumType.STRING)
    private Interest interest;
    private String interest_detail; //수정 필요
    private String name;
    private String image;
    private String comment;
    private String location;
    private String date;

    @ColumnDefault("1")
    private int meeting_cnt; //지속성 모임 : 모임 횟수
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
