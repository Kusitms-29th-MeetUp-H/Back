package com.kusitms29.backendH.domain.sync.application.controller;

import com.kusitms29.backendH.domain.clova.map.GeoLocation;
import com.kusitms29.backendH.domain.clova.map.GeoLocationService;
import com.kusitms29.backendH.domain.sync.application.controller.dto.response.SyncAssociateInfoResponseDto;
import com.kusitms29.backendH.domain.sync.application.controller.dto.response.SyncInfoResponseDto;
import com.kusitms29.backendH.domain.sync.application.service.SyncManageService;
import com.kusitms29.backendH.domain.user.ip.IpService;
import com.kusitms29.backendH.global.common.SuccessResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/sync")
@RestController
public class SyncManageController {
    private final SyncManageService syncManageService;
    private final IpService ipService;
    private final GeoLocationService geoLocationService;
    @GetMapping("/recommend")
    public ResponseEntity<SuccessResponse<?>> recommendSync(@RequestParam(name = "userId") Long userId, HttpServletRequest request) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
        String clientIp = ipService.getClientIpAddress(request);
        GeoLocation geoLocation = geoLocationService.getGeoLocation(clientIp);
        List<SyncInfoResponseDto> syncInfoResponseDtos = syncManageService.recommendSync(userId, clientIp);
        return SuccessResponse.ok(geoLocation);
    }
    @GetMapping("/friend")
    public ResponseEntity<SuccessResponse<?>> friendSync(@RequestParam(name = "take",defaultValue = "0") int take) {
        List<SyncInfoResponseDto> syncInfoResponseDtos = syncManageService.friendSync();
        List<SyncInfoResponseDto> dtos = syncManageService.getSyncInfoByTake(syncInfoResponseDtos, take);
        return SuccessResponse.ok(dtos);
    }
    @GetMapping("/associate")
    public ResponseEntity<SuccessResponse<?>> associateSync(@RequestParam(name = "take",defaultValue = "0") int take) {
        List<SyncAssociateInfoResponseDto> syncInfoResponseDtos = syncManageService.associateSync();
        List<SyncAssociateInfoResponseDto> dtos = syncManageService.getSyncInfoByTake(syncInfoResponseDtos, take);
        return SuccessResponse.ok(dtos);
    }
}
