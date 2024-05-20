package com.kusitms29.backendH.api.user.service.dto.response;

import com.kusitms29.backendH.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UserInfoResponseDto {
    private Long userId;
    private String image;
    private String name;
    private String university;
    private String syncType;
    private List<String> detailTypes;
    private String gender;

    public UserInfoResponseDto() {
        // 기본 생성자
    }

    public UserInfoResponseDto(Long userId, String image, String name, String university, String syncType, List<String> detailTypes, String gender) {
        this.userId = userId;
        this.image = image;
        this.name = name;
        this.university = university;
        this.syncType = syncType;
        this.detailTypes = detailTypes;
        this.gender = gender;
    }

    public static UserInfoResponseDto of(User user, List<String> detailTypes) {
        return new UserInfoResponseDto(user.getId(), user.getProfile(), user.getUserName(), user.getUniversity(), String.valueOf(user.getSyncType()), detailTypes, String.valueOf(user.getGender()));
    }

}