package com.kusitms29.backendH.domain.user.domain;

import com.kusitms29.backendH.domain.BaseEntity;
import com.kusitms29.backendH.domain.category.domain.Category;
import com.kusitms29.backendH.domain.sync.domain.Gender;
import com.kusitms29.backendH.domain.sync.domain.Language;
import com.kusitms29.backendH.domain.sync.domain.SyncType;
import com.kusitms29.backendH.domain.user.auth.PlatformUserInfo;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "user")
@Entity
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Enumerated(EnumType.STRING)
    private Platform platform;
    @Column(unique = true)
    private String platformId;
    private String email;
    private String name;
    private String profile;
    private String refreshToken;
    private String sessionId;

    @Enumerated(EnumType.STRING)
    private Language language;
    private String university;
    private String nationality;
    @Enumerated(EnumType.STRING)
    private Gender gender;

    //일회성, 지속성, 내친소
    @Enumerated(EnumType.STRING)
    private SyncType syncType;

    //관심사 전체
    //--UserCategory 연관개체--
    @ManyToMany
    @JoinTable(name = "UserCategory",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories;
    //--

    private String languageLevel;
    @ColumnDefault("0")
    private int sync_cnt;

    public static User createUser(PlatformUserInfo platformUserInfo, Platform platform, String sessionId) {
        return User.builder()
                .platformId(platformUserInfo.getId())
                .platform(platform)
                .email(platformUserInfo.getEmail())
                .name(platformUserInfo.getName())
                .profile(platformUserInfo.getPicture())
                .sessionId(sessionId)
                .build();
    }


    public void updatePlatform(Platform platform) {
        this.platform = platform;
    }


    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}

