package com.kusitms29.backendH.api.sync.controller;

import com.kusitms29.backendH.api.sync.service.SyncService;
import com.kusitms29.backendH.api.sync.service.dto.request.SyncInfoRequestDto;
import com.kusitms29.backendH.api.sync.service.dto.response.SyncAssociateInfoResponse;
import com.kusitms29.backendH.api.sync.service.dto.response.SyncAssociateInfoResponseDto;
import com.kusitms29.backendH.api.sync.service.dto.response.SyncInfoResponse;
import com.kusitms29.backendH.api.sync.service.dto.response.SyncInfoResponseDto;
import com.kusitms29.backendH.domain.user.ip.IpService;
import com.kusitms29.backendH.global.common.SuccessResponse;
import com.kusitms29.backendH.infra.config.auth.UserId;
import com.kusitms29.backendH.infra.external.clova.map.GeoLocationService;
import com.kusitms29.backendH.infra.utils.TranslateUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/sync")
@RestController
public class SyncMainController {
    private final SyncService syncManageService;
    private final IpService ipService;
    private final GeoLocationService geoLocationService;
    private final TranslateUtil translateUtil;
    @GetMapping("/recommend")
    public ResponseEntity<SuccessResponse<?>> recommendSync(@UserId Long userId,@RequestParam(name = "language", defaultValue = "한국어") String language, HttpServletRequest request) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
        String clientIp = ipService.getClientIpAddress(request);
//        GeoLocation geoLocation = geoLocationService.getGeoLocation(clientIp);
        List<SyncInfoResponseDto> syncInfoResponseDtos = syncManageService.recommendSync(userId, clientIp);
        if (language.equals("영어"))syncInfoResponseDtos=syncInfoResponseDtos.stream().map(syncInfoResponseDto -> translateUtil.translateObject(syncInfoResponseDto)).toList();
        return SuccessResponse.ok(syncInfoResponseDtos);
    }
    @PostMapping("/friend")
    public ResponseEntity<SuccessResponse<?>> friendSync(@UserId Long userId, @RequestBody SyncInfoRequestDto syncInfoRequestDto) {
        List<SyncInfoResponse> syncInfoResponseDtos = syncManageService.friendSync(userId, syncInfoRequestDto);
        if (syncInfoRequestDto.language().equals("영어"))syncInfoResponseDtos=syncInfoResponseDtos.stream().map(syncInfoResponseDto -> translateUtil.translateObject(syncInfoResponseDto)).toList();
        return SuccessResponse.ok(syncInfoResponseDtos);
    }
    @PostMapping("/search")
    public ResponseEntity<SuccessResponse<?>> searchSync(@UserId Long userId, @RequestBody SyncInfoRequestDto syncInfoRequestDto) {
        List<SyncInfoResponse> syncInfoResponseDtos = syncManageService.searchSync(userId, syncInfoRequestDto);
        if (syncInfoRequestDto.language().equals("영어"))syncInfoResponseDtos=syncInfoResponseDtos.stream().map(syncInfoResponseDto -> translateUtil.translateObject(syncInfoResponseDto)).toList();
        return SuccessResponse.ok(syncInfoResponseDtos);
    }
    @PostMapping("/associate")
    public ResponseEntity<SuccessResponse<?>> associateSync(@UserId Long userId, @RequestBody SyncInfoRequestDto syncInfoRequestDto) {
        List<SyncAssociateInfoResponse> syncInfoResponseDtos = syncManageService.associateSync(userId, syncInfoRequestDto);
        if (syncInfoRequestDto.language().equals("영어"))syncInfoResponseDtos=syncInfoResponseDtos.stream().map(syncInfoResponseDto -> translateUtil.translateObject(syncInfoResponseDto)).toList();
        return SuccessResponse.ok(syncInfoResponseDtos);
    }
}
