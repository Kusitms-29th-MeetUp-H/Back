package com.kusitms29.backendH.api.user.service.dto.response;

import com.kusitms29.backendH.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoResponseDto {
    private Long userId;
    private String image;
    private String name;
    private String university;

    public UserInfoResponseDto() {
        // 기본 생성자
    }

    public UserInfoResponseDto(Long userId, String image, String name, String university) {
        this.userId = userId;
        this.image = image;
        this.name = name;
        this.university = university;
    }

    public static UserInfoResponseDto of(User user) {
        return new UserInfoResponseDto(user.getId(), user.getProfile(), user.getUserName(), user.getUniversity());
    }

}