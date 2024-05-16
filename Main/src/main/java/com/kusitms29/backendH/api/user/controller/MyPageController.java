package com.kusitms29.backendH.api.user.controller;

import com.kusitms29.backendH.api.sync.service.dto.response.SyncInfoResponseDto;
import com.kusitms29.backendH.api.user.service.MyPageService;
import com.kusitms29.backendH.global.common.SuccessResponse;
import com.kusitms29.backendH.infra.config.auth.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
public class MyPageController {
    private final MyPageService myPageService;
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
}
