package com.kusitms29.backendH.api.user.service.dto.response;

import com.kusitms29.backendH.domain.user.entity.User;

public record UserInfoResponseDto(
        Long userId,
        String image,
        String name,
        String university
) {
    public static UserInfoResponseDto of(User user){
        return new UserInfoResponseDto(user.getId(), user.getProfile(), user.getUserName(), user.getUniversity());
    }
}
