package com.kusitms29.backendH.domain.user.application.controller;


import com.kusitms29.backendH.domain.user.application.controller.dto.request.CountryCalloutRequestDto;
import com.kusitms29.backendH.domain.user.application.controller.dto.request.EmailVerificationRequestDto;
import com.kusitms29.backendH.domain.user.application.controller.dto.request.ReceiverInfoRequestDto;
import com.kusitms29.backendH.domain.user.application.controller.dto.request.UserSignInRequestDto;
import com.kusitms29.backendH.domain.user.application.controller.dto.response.EmailVerificationResponseDto;
import com.kusitms29.backendH.domain.user.application.controller.dto.response.UserAuthResponseDto;
import com.kusitms29.backendH.domain.user.application.service.AuthService;
import com.kusitms29.backendH.domain.user.application.service.CountryDataService;
import com.kusitms29.backendH.domain.user.application.service.EmailVerificationService;
import com.kusitms29.backendH.global.common.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class UserController {
    private final AuthService authService;
    private final CountryDataService countryDataService;
    private final EmailVerificationService emailVerificationService;

    @PostMapping("/signin")
    public ResponseEntity<SuccessResponse<?>> signIn(@RequestHeader("Authorization") final String authToken,
                                                     @RequestHeader String fcmToken,
                                                     @RequestBody final UserSignInRequestDto requestDto) {
        final UserAuthResponseDto responseDto = authService.signIn(requestDto, authToken, fcmToken);
        return SuccessResponse.ok(responseDto);
    }
    @GetMapping("/countries")
    public ResponseEntity<SuccessResponse<?>> getCountries(@RequestBody CountryCalloutRequestDto requestDto) {
        List<String> countryNames = countryDataService.listOfCountries(requestDto.getPage(), requestDto.getPerPage(), requestDto.getLanguage());
        return SuccessResponse.ok(countryNames);
    }
    @PostMapping("/emails/verification-requests")
    public ResponseEntity<SuccessResponse<?>> sendMessage(@RequestBody ReceiverInfoRequestDto requestDto) {
        emailVerificationService.sendCodeToEmail(requestDto.getToEmail());
        return SuccessResponse.ok(true);
    }
    @GetMapping ("/emails/verifications")
    ResponseEntity<SuccessResponse<?>> verificationEmail(@RequestBody EmailVerificationRequestDto requestDto) {
        EmailVerificationResponseDto response = emailVerificationService.verifiedCode(requestDto.getEmail(), requestDto.getCode());
        return SuccessResponse.ok(response);
    }

}
