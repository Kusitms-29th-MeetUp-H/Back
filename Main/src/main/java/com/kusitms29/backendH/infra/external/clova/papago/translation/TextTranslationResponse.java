package com.kusitms29.backendH.infra.external.clova.papago.translation;

import lombok.Getter;

@Getter
public class TextTranslationResponse {
    public TextTranslationMessage message;

    @Getter
    public static class TextTranslationMessage {
        private TextTranslationResult result;

        @Getter
        public static class TextTranslationResult {
            private String srcLangType;
            private String tarLangType;
            private String translatedText;
        }
    }
}