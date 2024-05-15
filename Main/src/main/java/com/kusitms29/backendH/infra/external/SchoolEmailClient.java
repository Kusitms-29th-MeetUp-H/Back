package com.kusitms29.backendH.infra.external;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kusitms29.backendH.api.user.service.dto.request.schoolEmail.CalloutSchoolEmailRequestDto;
import com.kusitms29.backendH.api.user.service.dto.request.schoolEmail.CalloutSchoolEmailVerificationRequestDto;
import com.kusitms29.backendH.api.user.service.dto.request.schoolEmail.SchoolEmailRequestDto;
import com.kusitms29.backendH.api.user.service.dto.request.schoolEmail.SchoolEmailVerificationRequestDto;
import com.kusitms29.backendH.api.user.service.dto.response.schoolEmail.CalloutErrorResponse;
import com.kusitms29.backendH.api.user.service.dto.response.schoolEmail.CalloutSchoolEmailVerificationResponseDto;
import com.kusitms29.backendH.global.error.exception.InvalidValueException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static com.kusitms29.backendH.global.error.ErrorCode.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SchoolEmailClient {
    @Value("${unvicert.endpoint}")
    private String apiUrl;

    @Value("${unvicert.key}")
    private String key;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public CalloutErrorResponse callOutSendSchoolEmail(SchoolEmailRequestDto requestDto) {
        CalloutSchoolEmailRequestDto calloutSchoolEmailRequestDto = new CalloutSchoolEmailRequestDto(
                key, requestDto.getEmail(), requestDto.getUnivName(), true
        );
        HttpEntity<CalloutSchoolEmailRequestDto> entity = new HttpEntity<>(calloutSchoolEmailRequestDto);
        ResponseEntity<CalloutErrorResponse> response;
        try {
            response = restTemplate.exchange(
                    apiUrl + "/certify", HttpMethod.POST, entity, CalloutErrorResponse.class
            );
            return response.getBody();
        } catch(HttpClientErrorException.BadRequest ex) { //400 에러에 대한 에러 메세지 다양
            String message = "";
            try {
                JsonNode jsonNode = objectMapper.readTree(ex.getResponseBodyAsString());
                message = jsonNode.has("message") ? jsonNode.get("message").asText() : "Unknown error";
            } catch (Exception e) {
                log.info("Error parsing JSON: " + e.getMessage());
            }
            if(message.equals("이미 완료된 요청입니다.")) {
                throw new InvalidValueException(DUPLICATE_SCHOOL_MAIL);
            } else if(message.equals("대학과 일치하지 않는 메일 도메인입니다.")) {
                throw new InvalidValueException(INVALID_UNIVERSITY_DOMAIN);
            } else {
                throw new InvalidValueException(UNIVERSITY_API_FAIL_ERROR);
            }
        }
    }
    public CalloutSchoolEmailVerificationResponseDto callOutAuthSchoolEmail(SchoolEmailVerificationRequestDto requestDto) {
        CalloutSchoolEmailVerificationRequestDto calloutSchoolEmailVerificationRequestDto = new CalloutSchoolEmailVerificationRequestDto(
                key, requestDto.getEmail(), requestDto.getUnivName(), requestDto.getCode()
        );
        HttpEntity<CalloutSchoolEmailVerificationRequestDto> entity =  new HttpEntity<>(calloutSchoolEmailVerificationRequestDto);
        ResponseEntity<CalloutSchoolEmailVerificationResponseDto> response = restTemplate.exchange(
                apiUrl + "/certifycode", HttpMethod.POST, entity, CalloutSchoolEmailVerificationResponseDto.class
        );
        if(!response.getBody().isSuccess()) {
            throw new InvalidValueException(INVALID_AUTH_CODE);
        }
        return response.getBody();
    }

    public CalloutErrorResponse clearAuthCode() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("key", key);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody);

        ResponseEntity<CalloutErrorResponse> response;
        try {
            response = restTemplate.exchange(
                    apiUrl + "/clear", HttpMethod.POST, entity, CalloutErrorResponse.class
            );
            return response.getBody();
        } catch (HttpClientErrorException.BadRequest ex) {
            throw new InvalidValueException(UNIVERSITY_API_FAIL_ERROR);
        }
    }
}
