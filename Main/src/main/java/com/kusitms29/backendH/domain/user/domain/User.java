package com.kusitms29.backendH.domain.user.domain;

import com.kusitms29.backendH.domain.user.auth.PlatformUserInfo;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "user")
@Entity
public class User {
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

