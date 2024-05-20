package com.kusitms29.backendH.infra.external;

import com.kusitms29.backendH.api.user.service.dto.response.schoolEmail.CalloutErrorResponse;
import com.kusitms29.backendH.global.error.exception.InvalidValueException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static com.kusitms29.backendH.global.error.ErrorCode.INVALID_UNIVERSITY_NAME;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UniversityClient {
    @Value("${unvicert.endpoint}")
    private String apiUrl;

    @Value("${unvicert.key}")
    private String key;

    private final RestTemplate restTemplate;

    public void isValidUniversity(String university) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("univName", university);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody);
            ResponseEntity<CalloutErrorResponse> response = restTemplate.exchange(
                apiUrl + "/check", HttpMethod.POST, entity, CalloutErrorResponse.class
        );
        if(!response.getBody().isSuccess()) {
            throw new InvalidValueException(INVALID_UNIVERSITY_NAME);
        };
    }

}
