package com.kusitms29.backendH.domain.user.application.controller.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReceiverInfoRequestDto {
    private String toEmail;
}
