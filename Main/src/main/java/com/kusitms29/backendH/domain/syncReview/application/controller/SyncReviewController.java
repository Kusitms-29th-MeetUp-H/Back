package com.kusitms29.backendH.domain.syncReview.application.controller;

import com.kusitms29.backendH.domain.sync.application.controller.dto.response.SyncDetailResponseDto;
import com.kusitms29.backendH.domain.syncReview.application.service.SyncReviewService;
import com.kusitms29.backendH.domain.syncReview.application.service.dto.SyncReviewResponseDto;
import com.kusitms29.backendH.global.common.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sync/review")
@RequiredArgsConstructor
public class SyncReviewController {
    private final SyncReviewService syncReviewService;
    @GetMapping
    public ResponseEntity<SuccessResponse<?>> getSyncReviewList(@RequestParam(name = "syncId") Long syncId){
        List<SyncReviewResponseDto> syncReviewResponseDtos = syncReviewService.getSyncReviewList(syncId);
        return SuccessResponse.ok(syncReviewResponseDtos);
    }
}
