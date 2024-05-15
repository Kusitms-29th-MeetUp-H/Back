package com.kusitms29.backendH.infra.external.clova.papago;

import com.kusitms29.backendH.global.common.SuccessResponse;
import com.kusitms29.backendH.infra.external.clova.papago.detection.LanguageDetectionRequest;
import com.kusitms29.backendH.infra.external.clova.papago.detection.LanguageDetectionResponse;
import com.kusitms29.backendH.infra.external.clova.papago.translation.TextTranslationRequest;
import com.kusitms29.backendH.infra.external.clova.papago.translation.TextTranslationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/papago")
@RestController
public class PapagoController {
    private final PapagoService papagoService;

    @PostMapping("/translate")
    public ResponseEntity<SuccessResponse<?>> translateText(@RequestBody TextTranslationRequest requestDto) {
        TextTranslationResponse responseDto = papagoService.translateText(requestDto);
        return SuccessResponse.ok(responseDto.getMessage().getResult());
    }

    @PostMapping("/check-language")
    public ResponseEntity<SuccessResponse<?>> whatLanguageIsIt(@RequestBody LanguageDetectionRequest requestDto) {
        LanguageDetectionResponse responseDto = papagoService.checkLanguage(requestDto);
        return SuccessResponse.ok(responseDto);
    }

}
