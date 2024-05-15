package com.kusitms29.backendH.api.user.service.dto.request.schoolEmail;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class CalloutSchoolEmailRequestDto {
    private String key;
    private String email;
    private String univName;
    private Boolean univ_check;
}
