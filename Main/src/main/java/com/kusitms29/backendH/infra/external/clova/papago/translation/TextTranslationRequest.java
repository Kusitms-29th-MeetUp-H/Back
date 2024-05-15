package com.kusitms29.backendH.infra.external.clova.papago.translation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TextTranslationRequest {
    private String source; //원본 언어 코드
    private String target; //변역 결과 언어 코드
    private String text; //번역할 텍스트
}
