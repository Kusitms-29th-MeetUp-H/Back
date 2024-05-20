package com.kusitms29.backendH.api.user.controller;

import com.kusitms29.backendH.api.sync.service.dto.response.SyncInfoResponseDto;
import com.kusitms29.backendH.api.user.service.MyPageService;
import com.kusitms29.backendH.api.user.service.dto.request.CreateReviewRequest;
import com.kusitms29.backendH.api.user.service.dto.request.EditProfileRequest;
import com.kusitms29.backendH.api.user.service.dto.response.CreateReviewResponse;
import com.kusitms29.backendH.api.user.service.dto.response.UserInfoResponseDto;
import com.kusitms29.backendH.global.common.SuccessResponse;
import com.kusitms29.backendH.infra.config.auth.UserId;
import com.kusitms29.backendH.infra.utils.TranslateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
public class MyPageController {
    private final MyPageService myPageService;
    private final TranslateUtil translateUtil;
    @GetMapping("/mysync")
    public ResponseEntity<SuccessResponse<?>> getMySyncList(@UserId Long userId,@RequestParam(name = "take",defaultValue = "0") int take) {
        List< SyncInfoResponseDto> syncInfoResponseDtos = myPageService.getMySyncList(userId,take);
        return SuccessResponse.ok(syncInfoResponseDtos);
    }
    @GetMapping("/join")
    public ResponseEntity<SuccessResponse<?>> getJoinSyncList(@UserId Long userId,@RequestParam(name = "take",defaultValue = "0") int take) {
        List< SyncInfoResponseDto> syncInfoResponseDtos = myPageService.getJoinSyncList(userId,take);
        return SuccessResponse.ok(syncInfoResponseDtos);
    }
    @GetMapping
    public ResponseEntity<SuccessResponse<?>> getMyInfo(@UserId Long userId, @RequestParam (name = "language", defaultValue = "한국어")String language) {
        UserInfoResponseDto userInfoResponseDto = myPageService.getMyInfo(userId);
        if (language.equals("영어"))userInfoResponseDto=translateUtil.translateObject(userInfoResponseDto);
        return SuccessResponse.ok(userInfoResponseDto);
    }
    @PostMapping("/review")
    public ResponseEntity<SuccessResponse<?>> createReview(@UserId Long userId, @RequestBody CreateReviewRequest createReviewRequest) {
        CreateReviewResponse createReviewResponse = myPageService.createReview(userId,createReviewRequest);
        return SuccessResponse.created(createReviewResponse);
    }
    @GetMapping("/bookmark")
    public ResponseEntity<SuccessResponse<?>> getBookMarkSyncList(@UserId Long userId, @RequestParam(name = "take",defaultValue = "0") int take) {
        List<SyncInfoResponseDto> userInfoResponseDto = myPageService.getBookMarkSyncList(userId, take);
        return SuccessResponse.ok(userInfoResponseDto);
    }
    @PatchMapping
    public ResponseEntity<SuccessResponse<?>> editBoard(@UserId Long userId, @ModelAttribute EditProfileRequest editProfileRequest) {
        myPageService.editProfile(userId, editProfileRequest);
        return SuccessResponse.ok("UPDATE");
    }
}
