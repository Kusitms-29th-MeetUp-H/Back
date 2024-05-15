package com.kusitms29.backendH.api.sync.controller;

import com.kusitms29.backendH.api.sync.service.SyncService;
import com.kusitms29.backendH.api.sync.service.dto.request.SyncInfoRequestDto;
import com.kusitms29.backendH.api.sync.service.dto.response.SyncAssociateInfoResponseDto;
import com.kusitms29.backendH.api.sync.service.dto.response.SyncInfoResponseDto;
import com.kusitms29.backendH.domain.user.ip.IpService;
import com.kusitms29.backendH.global.common.SuccessResponse;
import com.kusitms29.backendH.infra.external.clova.map.GeoLocationService;
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
    @GetMapping("/recommend")
    public ResponseEntity<SuccessResponse<?>> recommendSync(@RequestParam(name = "userId") Long userId, HttpServletRequest request) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
        String clientIp = ipService.getClientIpAddress(request);
//        GeoLocation geoLocation = geoLocationService.getGeoLocation(clientIp);
        List<SyncInfoResponseDto> syncInfoResponseDtos = syncManageService.recommendSync(userId, clientIp);
        return SuccessResponse.ok(syncInfoResponseDtos);
    }
    @PostMapping("/friend")
    public ResponseEntity<SuccessResponse<?>> friendSync(@RequestBody SyncInfoRequestDto syncInfoRequestDto) {
        List<SyncInfoResponseDto> syncInfoResponseDtos = syncManageService.friendSync(syncInfoRequestDto);
        return SuccessResponse.ok(syncInfoResponseDtos);
    }
    @PostMapping("/search")
    public ResponseEntity<SuccessResponse<?>> searchSync(@RequestBody SyncInfoRequestDto syncInfoRequestDto) {
        List<SyncInfoResponseDto> syncInfoResponseDtos = syncManageService.searchSync(syncInfoRequestDto);
        return SuccessResponse.ok(syncInfoResponseDtos);
    }
    @PostMapping("/associate")
    public ResponseEntity<SuccessResponse<?>> associateSync(@RequestBody SyncInfoRequestDto syncInfoRequestDto) {
        List<SyncAssociateInfoResponseDto> syncInfoResponseDtos = syncManageService.associateSync(syncInfoRequestDto);
        return SuccessResponse.ok(syncInfoResponseDtos);
    }
}
