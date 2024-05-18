package com.kusitms29.backendH.infra.utils;

import com.kusitms29.backendH.infra.external.clova.papago.PapagoService;
import com.kusitms29.backendH.infra.external.clova.papago.translation.TextTranslationRequest;
import com.kusitms29.backendH.infra.external.clova.papago.translation.TextTranslationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

@Service
@RequiredArgsConstructor
public class TranslateUtil {
    private final PapagoService papagoService;

    public <T> T translateObject(T object) {
        T translatedObject = createNewInstance(object);
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            Class<?> fieldType = field.getType();
            if (fieldType == String.class) {
                field.setAccessible(true);
                try {
                    String value = (String) field.get(object);
                    if (value != null) {
                        TextTranslationRequest requestDto = new TextTranslationRequest();
                        requestDto.setSource("ko");
                        requestDto.setTarget("en");
                        requestDto.setText(value);
                        TextTranslationResponse translationResponse = papagoService.translateText(requestDto);
                        field.set(translatedObject, translationResponse.getMessage().getResult().getTranslatedText());
                    } else {
                        field.set(translatedObject, null);
                    }
                } catch (IllegalAccessException e) {
                    // log.error("Error while translating object", e);
                }
            } else {
                field.setAccessible(true);
                try {
                    Object value = field.get(object);
                    field.set(translatedObject, value);
                } catch (IllegalAccessException e) {
                    // log.error("Error while setting non-string field", e);
                }
            }
        }
        return translatedObject;
    }

    @SuppressWarnings("unchecked")
    private <T> T createNewInstance(T object) {
        try {
            Class<T> clazz = (Class<T>) object.getClass();
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            // log.error("Error while creating new instance", e);
            throw new RuntimeException("Failed to create new instance", e);
        }
    }
}