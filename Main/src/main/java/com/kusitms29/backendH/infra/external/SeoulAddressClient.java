package com.kusitms29.backendH.infra.external;

import com.kusitms29.backendH.api.sync.service.dto.response.SeoulAddressResponse;
import com.kusitms29.backendH.global.error.exception.InternalServerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static com.kusitms29.backendH.global.error.ErrorCode.SEOUL_ADDRESS_FAIL_ERROR;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SeoulAddressClient {
    @Value("${address.endpoint}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public SeoulAddressResponse calloutSeoulAddressAPI() {

        URI apiUrlWithQuery = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("regcode_pattern", "11*00000")
                .queryParam("is_ignore_zero", true)
                .build()
                .toUri();

        try {
            ResponseEntity<SeoulAddressResponse> seoulAddressResponse = restTemplate.exchange(
                    apiUrlWithQuery, HttpMethod.GET, null, SeoulAddressResponse.class
            );
            return seoulAddressResponse.getBody();
        } catch (RestClientException e) {
            throw new InternalServerException(SEOUL_ADDRESS_FAIL_ERROR);
        }
    }
}
