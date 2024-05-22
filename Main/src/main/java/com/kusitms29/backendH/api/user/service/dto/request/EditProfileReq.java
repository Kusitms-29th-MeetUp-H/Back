package com.kusitms29.backendH.api.user.service.dto.request;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record EditProfileReq(
        String image,
        String name,
        String gender,
        String syncType,
        List<String> detailTypes
) {
}
