package com.kusitms29.backendH.domain.user.application.controller.dto.response.schoolEmail;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CalloutErrorResponse {
    private String status;
    private boolean success;
    private String message;
}
