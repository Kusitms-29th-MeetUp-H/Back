package com.kusitms29.backendH.application.user.service.dto.response.schoolEmail;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CalloutErrorResponse {
    private String status;
    private boolean success;
    private String message;
}
