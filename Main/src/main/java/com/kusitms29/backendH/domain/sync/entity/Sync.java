package com.kusitms29.backendH.domain.sync.entity;

import com.kusitms29.backendH.domain.category.entity.Type;
import com.kusitms29.backendH.global.common.BaseEntity;
import com.kusitms29.backendH.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.time.LocalTime;

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
    private String regularDay;
    private LocalTime regularTime;
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
    //일단 제휴가 뭐 없어서 일단 이렇게 함
    private String associate;

    public enum Status {
        RECRUITING, COMPLETED, DELETED;
    }

    public static Sync from(Long syncId) {
        return new Sync(syncId,null,null,null,null,null,null,null,null,null,null,null,null,null,0,0,null,null,null,null);
    }

    public static Sync createSync(User user, String userIntro, String syncIntro, SyncType syncType,
                                  String syncName, String image, String location, LocalDateTime date,
                                  String regularDay, LocalTime regularTime, LocalDateTime routineDate,
                                  int member_min, int member_max,
                                  Type type, String detailType){
        return Sync.builder()
                .user(user)
                .userIntro(userIntro)
                .syncIntro(syncIntro)
                .syncType(syncType)
                .syncName(syncName)
                .image(image)
                .location(location)
                .date(date)
                .regularDay(regularDay)
                .regularTime(regularTime)
                .routineDate(routineDate)
                .member_min(member_min)
                .member_max(member_max)
                .type(type)
                .detailType(detailType)
                .build();
    }

    public void updateNextDate(LocalDateTime routineDate) {
        this.routineDate = routineDate;
    }

}
