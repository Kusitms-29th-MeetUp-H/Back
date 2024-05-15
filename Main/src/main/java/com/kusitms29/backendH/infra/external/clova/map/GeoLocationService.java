package com.kusitms29.backendH.infra.external.clova.map;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class GeoLocationService {
    private final RestTemplate restTemplate;
    @Value("${api.cloud.geolocation.endpoint}")
    private String endpoint;
    @Value("${api.cloud.geolocation.access-key}")
    private String accessKey;
    @Value("${api.cloud.geolocation.secret-key}")
    private String secretKey;

//    public GeoLocationService(RestTemplateBuilder restTemplateBuilder,
//                              @Value("${naver.cloud.geolocation.endpoint}") String endpoint,
//                              @Value("${naver.cloud.geolocation.access-key}") String accessKey,
//                              @Value("${naver.cloud.geolocation.secret-key}") String secretKey) {
//        this.restTemplate = restTemplateBuilder.build();
//        this.endpoint = endpoint;
//        this.accessKey = accessKey;
//        this.secretKey = secretKey;
//    }
public GeoLocation getGeoLocation(String ip) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
    String url = endpoint + "/geolocation/v2/geoLocation";
    String timestamp = String.valueOf(System.currentTimeMillis());
    String signature = generateSignature(timestamp);

    HttpHeaders headers = new HttpHeaders();
    headers.set("x-ncp-apigw-timestamp", timestamp);
    headers.set("x-ncp-iam-access-key", accessKey);
    headers.set("x-ncp-apigw-signature-v2", signature);

    // 요청 파라미터 설정
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("ip", "219.255.158.170");
//        params.add("ext", "t");
//        params.add("responseFormatType", "json");

    // 헤더와 파라미터를 포함한 HttpEntity 생성
    HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

    ResponseEntity<GeoLocationResponse> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            requestEntity,
            GeoLocationResponse.class);

    return response.getBody().getGeoLocation();
}
//    public GeoLocation getGeoLocation(String ip) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
//        String url = "https://geolocation.apigw.ntruss.com/geolocation/v2/geoLocation";
//        String timestamp = String.valueOf(System.currentTimeMillis());
//        String signature = generateSignature(timestamp);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("x-ncp-apigw-timestamp", timestamp);
//        headers.set("x-ncp-iam-access-key", accessKey);
//        headers.set("x-ncp-apigw-signature-v2", signature);
//
//        // 요청 파라미터 설정
//        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
//                .queryParam("ip", "219.255.158.170")
//                .queryParam("ext", "t")
//                .queryParam("responseFormatType", "json");
//
//        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
//
//        ResponseEntity<GeoLocationResponse> response = restTemplate.exchange(
//                builder.toUriString(),
//                HttpMethod.GET,
//                requestEntity,
//                GeoLocationResponse.class);
//
//        return response.getBody().getGeoLocation();
//    }

    private String generateSignature(String timestamp) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, UnsupportedEncodingException {
        String space = " ";
        String newLine = "\n";
        String method = "GET";
        String url = "/geolocation/v2/geoLocation";

        String message = new StringBuilder()
                .append(method)
                .append(space)
                .append(url)
                .append(newLine)
                .append(timestamp)
                .append(newLine)
                .append(accessKey)
                .toString();

        SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);

        byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
        String encodeBase64String = Base64.getEncoder().encodeToString(rawHmac);

        return encodeBase64String;
    }
}
