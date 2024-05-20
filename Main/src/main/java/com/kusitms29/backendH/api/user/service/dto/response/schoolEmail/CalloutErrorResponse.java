package com.kusitms29.backendH.api.user.service.dto.response.schoolEmail;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CalloutErrorResponse {
    private String status;
    private boolean success;
    private String message;
}