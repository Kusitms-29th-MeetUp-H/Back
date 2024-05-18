package com.kusitms29.backendH.domain.user.entity;

import com.kusitms29.backendH.global.common.BaseEntity;
import com.kusitms29.backendH.domain.sync.entity.Gender;
import com.kusitms29.backendH.domain.sync.entity.Language;
import com.kusitms29.backendH.domain.sync.entity.SyncType;
import com.kusitms29.backendH.domain.user.auth.PlatformUserInfo;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Setter
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
    private String userName;
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

    private String location;

    private String languageLevel;

    public static User createUser(PlatformUserInfo platformUserInfo, Platform platform, String sessionId) {
        return User.builder()
                .platformId(platformUserInfo.getId())
                .platform(platform)
                .email(platformUserInfo.getEmail())
                .userName(platformUserInfo.getName())
                .profile(platformUserInfo.getPicture())
                .sessionId(sessionId)
                .build();
    }
    public static User from(Long userId) {
        return new User(userId,null,null,null,null,null,null,null,null,null,null,null,null,null,null);
    }

    public void updateOnBoardingWithoutCategory(String language, String profileImage, String userName, String countryName, String gender,
                                                String university, String email, String sycnType) {
        this.setLanguage(Language.valueOf(language));
        this.setProfile(profileImage);
        this.setUserName(userName);
        this.setNationality(countryName);
        this.setGender(Gender.valueOf(gender));
        this.setUniversity(university);
        this.setEmail(email);
        this.setSyncType(SyncType.valueOf(sycnType));
    }
    public void updateProfile(String profile, String name, Gender gender, SyncType sycnType){
        this.profile = profile;
        this.userName = name;
        this.gender = gender;
        this.syncType = sycnType;
    }

    public void updatePlatform(Platform platform) {
        this.platform = platform;
    }


    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}

