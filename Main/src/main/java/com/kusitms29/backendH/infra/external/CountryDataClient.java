package com.kusitms29.backendH.infra.external;

import com.kusitms29.backendH.api.user.service.dto.response.CountryResponseDto;
import com.kusitms29.backendH.domain.sync.entity.Language;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class CountryDataClient {
    @Value("${openData.url}")
    private String apiUrl;
    @Value("${openData.authorization}")
    private String authorization;

    private final RestTemplate restTemplate;
    public List<String> listOfCountries(Integer page, Integer perPage, String language) {
        Language lan = Language.getEnumLanguageFromStringLanguage(language);
        CountryResponseDto countryResponseDto = calloutCountryAPI(page, perPage);
        return countryResponseDto.getData().stream()
                .map(data -> lan.getStringLanguage().equals("korean") ? data.get한글명() : data.get영문명())
                .toList();
    }
    private CountryResponseDto calloutCountryAPI(Integer page, Integer perPage) {
        StringBuilder authSB = new StringBuilder();
        authSB.append("Infuser "); authSB.append(authorization);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authSB.toString());

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(headers);

        URI apiUrlWithQuery = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("page", page)
                .queryParam("perPage", perPage)
                .build()
                .toUri();

        ResponseEntity<CountryResponseDto> countryResponse = restTemplate.exchange(
                apiUrlWithQuery, HttpMethod.GET, requestEntity, CountryResponseDto.class
        );
        return countryResponse.getBody();
    }

}
