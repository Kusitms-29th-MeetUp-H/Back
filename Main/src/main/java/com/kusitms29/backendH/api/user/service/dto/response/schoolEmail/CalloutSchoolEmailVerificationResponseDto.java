package com.kusitms29.backendH.api.user.service.dto.response.schoolEmail;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CalloutSchoolEmailVerificationResponseDto {
    private boolean success;
    private String univName;
    private String certified_email;
    private String certified_date;
}
