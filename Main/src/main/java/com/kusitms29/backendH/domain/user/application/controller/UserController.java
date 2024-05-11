package com.kusitms29.backendH.domain.user.application.controller;


import com.kusitms29.backendH.domain.user.application.controller.dto.request.*;
import com.kusitms29.backendH.domain.user.application.controller.dto.request.schoolEmail.SchoolEmailRequestDto;
import com.kusitms29.backendH.domain.user.application.controller.dto.request.schoolEmail.SchoolEmailVerificationRequestDto;
import com.kusitms29.backendH.domain.user.application.controller.dto.response.*;
import com.kusitms29.backendH.domain.user.application.controller.dto.response.schoolEmail.CalloutErrorResponse;
import com.kusitms29.backendH.domain.user.application.controller.dto.response.schoolEmail.CalloutSchoolEmailVerificationResponseDto;
import com.kusitms29.backendH.domain.user.application.service.*;
import com.kusitms29.backendH.global.common.SuccessResponse;
import com.kusitms29.backendH.infra.config.auth.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class UserController {
    private final UserService userService;
    private final AuthService authService;
    private final CountryDataService countryDataService;
    private final EmailVerificationService emailVerificationService;
    private final SchoolEmailService schoolEmailService;
    private final UniversitySerivce universitySerivce;

    @PostMapping("/signin")
    public ResponseEntity<SuccessResponse<?>> signIn(@RequestHeader("Authorization") final String authToken,
                                                     @RequestHeader String fcmToken,
                                                     @RequestBody final UserSignInRequestDto requestDto) {
        final UserAuthResponseDto responseDto = authService.signIn(requestDto, authToken, fcmToken);
        return SuccessResponse.ok(responseDto);
    }
    @PostMapping("/onboarding")
    public ResponseEntity<SuccessResponse<?>> onboarding(@UserId Long userId,
                                                         @RequestPart("profileImage") MultipartFile profileImage,
                                                         @RequestPart("onBoardingRequest") OnBoardingRequestDto requestDto) {
         OnBoardingResponseDto responseDto = userService.onBoardingUser(userId, profileImage, requestDto);
         return SuccessResponse.ok(responseDto);
    }

    @PostMapping("/valid-university")
    public ResponseEntity<SuccessResponse<?>> isItValidUniversity(@RequestBody UniversityRequestDto requestDto) {
        universitySerivce.isValidUniversity(requestDto.getUnivName());
        return SuccessResponse.ok(true);
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

    @PostMapping("/school-emails/verification-requests")
    public ResponseEntity<SuccessResponse<?>> sendMessageToSchool(@RequestBody SchoolEmailRequestDto requestDto) {
        CalloutErrorResponse responseDto = schoolEmailService.callOutSendSchoolEmail(requestDto);
        return SuccessResponse.ok(responseDto.isSuccess());
    }

    @PostMapping("/school-emails/verifications")
    public ResponseEntity<SuccessResponse<?>> verificationSchoolEmail(@RequestBody SchoolEmailVerificationRequestDto requestDto) {
        CalloutSchoolEmailVerificationResponseDto responseDto = schoolEmailService.callOutAuthSchoolEmail(requestDto);
        return SuccessResponse.ok(responseDto);
    }

    @PostMapping("/school-emails/reset")
    public ResponseEntity<SuccessResponse<?>> resetForTryEmailTest() {
        CalloutErrorResponse responseDto = schoolEmailService.clearAuthCode();
        return SuccessResponse.ok(responseDto.isSuccess());
    }



}
