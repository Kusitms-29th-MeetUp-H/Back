//package com.kusitms29.backendH.domain.participation.application.controller;
//
//import com.kusitms29.backendH.domain.participation.application.controller.response.EnterSyncResponseDto;
//import com.kusitms29.backendH.domain.participation.application.service.ParticipationService;
//import com.kusitms29.backendH.global.common.SuccessResponse;
//import com.kusitms29.backendH.infra.config.auth.UserId;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RequiredArgsConstructor
//@RequestMapping("/api/participation")
//@RestController
//public class ParticipationController {
//    private final ParticipationService participationService;
//    @PostMapping("/{syncId}")
//    public ResponseEntity<SuccessResponse<?>> participateToSync(@UserId Long userId, @PathVariable Long syncId) {
//        EnterSyncResponseDto newParticipation = participationService.enterSync(userId, syncId);
//        return SuccessResponse.created(newParticipation);
//    }
//}
