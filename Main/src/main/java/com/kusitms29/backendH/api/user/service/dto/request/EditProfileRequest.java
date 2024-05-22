package com.kusitms29.backendH.api.user.service.dto.request;

import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
public record EditProfileRequest(
        String userName,
        String gender,
        String syncType,
        List<String> detailTypes
) {
}
