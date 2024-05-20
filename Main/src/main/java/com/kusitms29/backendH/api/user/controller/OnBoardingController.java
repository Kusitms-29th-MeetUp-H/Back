package com.kusitms29.backendH.api.user.controller;

import com.kusitms29.backendH.api.user.service.OnBoardingService;
import com.kusitms29.backendH.api.user.service.dto.request.CountryCalloutRequestDto;
import com.kusitms29.backendH.api.user.service.dto.request.OnBoardingRequestDto;
import com.kusitms29.backendH.api.user.service.dto.request.UniversityRequestDto;
import com.kusitms29.backendH.api.user.service.dto.request.schoolEmail.SchoolEmailRequestDto;
import com.kusitms29.backendH.api.user.service.dto.request.schoolEmail.SchoolEmailVerificationRequestDto;
import com.kusitms29.backendH.api.user.service.dto.response.OnBoardingResponseDto;
import com.kusitms29.backendH.api.user.service.dto.response.schoolEmail.CalloutErrorResponse;
import com.kusitms29.backendH.api.user.service.dto.response.schoolEmail.CalloutSchoolEmailVerificationResponseDto;
import com.kusitms29.backendH.global.common.SuccessResponse;
import com.kusitms29.backendH.infra.config.auth.UserId;
import com.kusitms29.backendH.infra.external.CountryDataClient;
import com.kusitms29.backendH.infra.external.SchoolEmailClient;
import com.kusitms29.backendH.infra.external.UniversityClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@RequestMapping("/api/user")
@RequiredArgsConstructor
@RestController
public class OnBoardingController {
    private final OnBoardingService onBoardingService;
    private final UniversityClient universityClient;
    private final CountryDataClient countryDataClient;
    private final SchoolEmailClient schoolEmailClient;

    @PostMapping("/onboarding")
    public ResponseEntity<SuccessResponse<?>> onboarding(@UserId Long userId,
                                                         @RequestPart("profileImage") MultipartFile profileImage,
                                                         @RequestPart("onBoardingRequest") OnBoardingRequestDto requestDto) {
        onBoardingService.onBoardingUser(userId, profileImage, requestDto);
        return SuccessResponse.created("success");
    }
    @PostMapping("/valid-university")
    public ResponseEntity<SuccessResponse<?>> isItValidUniversity(@RequestBody UniversityRequestDto requestDto) {
        universityClient.isValidUniversity(requestDto.getUnivName());
        return SuccessResponse.ok(true); //주석
    }

    @PostMapping("/countries")
    public ResponseEntity<SuccessResponse<?>> getCountries(@RequestBody CountryCalloutRequestDto requestDto) {
        List<String> countryNames = countryDataClient.listOfCountries(requestDto.getPage(), requestDto.getPerPage(), requestDto.getLanguage());
        return SuccessResponse.ok(countryNames);
    }


    @PostMapping("/school-emails/verification-requests")
    public ResponseEntity<SuccessResponse<?>> sendMessageToSchool(@RequestBody SchoolEmailRequestDto requestDto) {
        CalloutErrorResponse responseDto = schoolEmailClient.callOutSendSchoolEmail(requestDto);
        return SuccessResponse.ok(responseDto.isSuccess());
    }

    @PostMapping("/school-emails/verifications")
    public ResponseEntity<SuccessResponse<?>> verificationSchoolEmail(@RequestBody SchoolEmailVerificationRequestDto requestDto) {
        CalloutSchoolEmailVerificationResponseDto responseDto = schoolEmailClient.callOutAuthSchoolEmail(requestDto);
        return SuccessResponse.ok(responseDto);
    }

    @PostMapping("/school-emails/reset")
    public ResponseEntity<SuccessResponse<?>> resetForTryEmailTest() {
        CalloutErrorResponse responseDto = schoolEmailClient.clearAuthCode();
        return SuccessResponse.ok(responseDto.isSuccess());
    }
}
