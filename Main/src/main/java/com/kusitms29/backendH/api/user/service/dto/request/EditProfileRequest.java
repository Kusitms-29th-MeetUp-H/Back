package com.kusitms29.backendH.api.user.service.dto.request;

import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
public record EditProfileRequest(
        MultipartFile image,
        String name,
        String gender,
        String syncType,
        List<String> detailTypes
) {
}
