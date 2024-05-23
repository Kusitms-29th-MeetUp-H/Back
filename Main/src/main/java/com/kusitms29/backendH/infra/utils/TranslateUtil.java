package com.kusitms29.backendH.infra.utils;

import com.kusitms29.backendH.infra.external.clova.papago.PapagoService;
import com.kusitms29.backendH.infra.external.clova.papago.translation.TextTranslationRequest;
import com.kusitms29.backendH.infra.external.clova.papago.translation.TextTranslationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

@Service
@RequiredArgsConstructor
public class TranslateUtil {
    private final PapagoService papagoService;

    public <T> T translateObject(T object) {
        if (object == null) {
            return null;
        }
        T translatedObject = createNewInstance(object);
        if (translatedObject != null) {
            translateFields(object, translatedObject);
        }
        return translatedObject;
    }

    private <T> void translateFields(T object, T translatedObject) {
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            Class<?> fieldType = field.getType();
            if (fieldType == String.class) {
                field.setAccessible(true);
                try {
                    String value = (String) field.get(object);
                    if (value != null && !value.isEmpty()) {
                        TextTranslationRequest requestDto = new TextTranslationRequest();
                        requestDto.setSource("ko");
                        requestDto.setTarget("en");
                        requestDto.setText(value.trim());
                        TextTranslationResponse translationResponse = papagoService.translateText(requestDto);
                        field.set(translatedObject, translationResponse.getMessage().getResult().getTranslatedText());
                    } else {
                        field.set(translatedObject, value);
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
    }

    @SuppressWarnings("unchecked")
    private <T> T createNewInstance(T object) {
        try {
            Class<T> clazz = (Class<T>) object.getClass();
            return clazz.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException e) {
            // 기본 생성자가 없는 경우 처리
            return null;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            // 객체 생성 실패 시 처리
            throw new RuntimeException("Failed to create new instance of " + object.getClass().getName(), e);
        }
    }
}