package com.kusitms29.backendH.infra.utils;

import com.kusitms29.backendH.infra.external.clova.papago.PapagoService;
import com.kusitms29.backendH.infra.external.clova.papago.translation.TextTranslationRequest;
import com.kusitms29.backendH.infra.external.clova.papago.translation.TextTranslationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

@Service
public class TranslateUtil {
    private PapagoService papagoService;
    private Properties promptMap = new Properties();

    public TranslateUtil(PapagoService papagoService) {
        this.papagoService = papagoService;
        loadPromptMap(); // 프롬프트 파일 로드
    }

    private void loadPromptMap() {
        try (InputStream inputStream = getClass().getResourceAsStream("/prompt.properties")) {
            promptMap.load(inputStream);
            System.out.println("Prompt Map: " + promptMap);
        } catch (IOException e) {
            // log.error("Error loading prompt map", e);
        }
    }

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
                        String translationPrompt = promptMap.getProperty(value);
                        if (translationPrompt != null) {
                            // 프롬프트 매핑이 있는 경우, 프롬프트를 그대로 반환
                            field.set(translatedObject, translationPrompt);
                        } else {
                            // 프롬프트 매핑이 없는 경우, 번역 서비스 사용
                            TextTranslationRequest requestDto = new TextTranslationRequest();
                            requestDto.setSource("ko");
                            requestDto.setTarget("en");
                            requestDto.setText(value.trim());
                            TextTranslationResponse translationResponse = papagoService.translateText(requestDto);
                            field.set(translatedObject, translationResponse.getMessage().getResult().getTranslatedText());
                        }
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