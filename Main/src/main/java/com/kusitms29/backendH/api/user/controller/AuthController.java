package com.kusitms29.backendH.api.user.controller;

import com.kusitms29.backendH.api.user.service.AuthService;
import com.kusitms29.backendH.api.user.service.dto.request.UserSignInRequestDto;
import com.kusitms29.backendH.api.user.service.dto.response.UserAuthResponseDto;
import com.kusitms29.backendH.global.common.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/auth")
@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthService authService;
    @PostMapping("/signin")
    public ResponseEntity<SuccessResponse<?>> signIn(@RequestHeader("Authorization") final String authToken,
                                                     @RequestHeader String fcmToken,
                                                     @RequestBody final UserSignInRequestDto requestDto) {
        final UserAuthResponseDto responseDto = authService.signIn(requestDto, authToken, fcmToken);
        return SuccessResponse.ok(responseDto);
    }
}
