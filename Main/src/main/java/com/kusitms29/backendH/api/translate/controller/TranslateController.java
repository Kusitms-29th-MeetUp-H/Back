package com.kusitms29.backendH.api.translate.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kusitms29.backendH.api.translate.service.TranslateService;
import com.kusitms29.backendH.api.translate.service.dto.TranslateReqDto;
import com.kusitms29.backendH.api.translate.service.dto.TranslateResDto;
import com.kusitms29.backendH.global.common.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/translate")
public class TranslateController {
    private final TranslateService translateService;
    @PostMapping
    public ResponseEntity<SuccessResponse<?>> translate(@RequestBody TranslateReqDto translateReqDto) throws JsonProcessingException {
        TranslateResDto translateResDto = translateService.translateMessage(translateReqDto);
        return SuccessResponse.ok(translateResDto);
    }
}
