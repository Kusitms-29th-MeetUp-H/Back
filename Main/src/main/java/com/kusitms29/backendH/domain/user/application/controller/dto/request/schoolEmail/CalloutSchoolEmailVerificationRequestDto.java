package com.kusitms29.backendH.domain.user.application.controller.dto.request.schoolEmail;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class CalloutSchoolEmailVerificationRequestDto {
    private String key;
    private String email;
    private String univName;
    private int code;
}
