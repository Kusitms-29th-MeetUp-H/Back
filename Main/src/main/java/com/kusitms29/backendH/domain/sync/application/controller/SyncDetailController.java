package com.kusitms29.backendH.domain.sync.application.controller;

import com.kusitms29.backendH.domain.sync.application.controller.dto.response.SyncDetailResponseDto;
import com.kusitms29.backendH.domain.sync.application.service.SyncDetailService;
import com.kusitms29.backendH.global.common.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/sync/detail")
@RestController
public class SyncDetailController {
    private final SyncDetailService syncDetailService;
    @GetMapping
    public ResponseEntity<SuccessResponse<?>> syncDetail(@RequestParam(name = "syncId") Long syncId){
        SyncDetailResponseDto syncDetailResponseDto = syncDetailService.getSyncDetail(syncId);
        return SuccessResponse.ok(syncDetailResponseDto);
    }
}
