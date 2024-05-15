package com.kusitms29.backendH.infra.external.clova.papago;

import com.kusitms29.backendH.global.error.exception.InvalidValueException;
import com.kusitms29.backendH.infra.external.clova.papago.detection.LanguageDetectionRequest;
import com.kusitms29.backendH.infra.external.clova.papago.detection.LanguageDetectionResponse;
import com.kusitms29.backendH.infra.external.clova.papago.translation.TextTranslationRequest;
import com.kusitms29.backendH.infra.external.clova.papago.translation.TextTranslationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static com.kusitms29.backendH.global.error.ErrorCode.PAPAGO_FAIL_ERROR;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class PapagoService {

    private final RestTemplate restTemplate;
    @Value("${api.cloud.textTranslation.endpoint}")
    private String endpoint;
    @Value("${api.cloud.textTranslation.access-key}")
    private String accessKey;
    @Value("${api.cloud.textTranslation.secret-key}")
    private String secretKey;

    public TextTranslationResponse translateText(TextTranslationRequest requestDto) {
        String url = endpoint + "/nmt/v1/translation";
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-NCP-APIGW-API-KEY-ID", accessKey);
        headers.set("X-NCP-APIGW-API-KEY", secretKey);

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("source", requestDto.getSource());
        requestBody.put("target", requestDto.getTarget());
        requestBody.put("text", requestDto.getText());
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<TextTranslationResponse> response;
        try {
            response = restTemplate.exchange(
                    url, HttpMethod.POST, entity, TextTranslationResponse.class
            );

            log.info("Response status code: {}", response.getStatusCode());
            log.info("Response headers: {}", response.getHeaders());
            log.info("Response body: {}", response.getBody());
            if(response.getBody() != null) {
                return response.getBody();
            }
            return response.getBody();
        } catch (HttpClientErrorException e) {
            log.info("e.getMessage() :: " + e.getMessage());
            throw new InvalidValueException(PAPAGO_FAIL_ERROR);
        }

    }
    public LanguageDetectionResponse checkLanguage(LanguageDetectionRequest requestDto) {
        String url = endpoint + "/langs/v1/dect";
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-NCP-APIGW-API-KEY-ID", accessKey);
        headers.set("X-NCP-APIGW-API-KEY", secretKey);

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("query", requestDto.getQuery());
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<LanguageDetectionResponse> response;
        try {
            response = restTemplate.exchange(
                    url, HttpMethod.POST, entity, LanguageDetectionResponse.class
            );

            log.info("Response status code: {}", response.getStatusCode());
            log.info("Response headers: {}", response.getHeaders());
            log.info("Response body: {}", response.getBody());
            if(response.getBody() != null) {
                return response.getBody();
            }
            return response.getBody();
        } catch (HttpClientErrorException e) {
            log.info("e.getMessage() :: " + e.getMessage());
            throw new InvalidValueException(PAPAGO_FAIL_ERROR);
        }
    }

}
