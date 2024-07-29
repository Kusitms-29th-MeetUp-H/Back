package com.kusitms29.backendH.api.translate.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kusitms29.backendH.api.translate.service.dto.TranslateReqDto;
import com.kusitms29.backendH.api.translate.service.dto.TranslateResDto;
import com.kusitms29.backendH.infra.external.windyflo.WindyfloClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TranslateService {
    private final WindyfloClient windyfloClient;
    private final ObjectMapper objectMapper;

    public TranslateResDto translateMessage(TranslateReqDto translateReqDto) throws JsonProcessingException {
        JsonNode message = windyfloClient.translateMessage(translateReqDto);
        return extractReviewGitRes(message);
    }
    public TranslateResDto extractReviewGitRes(Object input) throws JsonProcessingException {
        JsonNode rootNode = objectMapper.readTree(input.toString());
        JsonNode jsonNode = rootNode.get("json");
        String message = jsonNode.get("message").asText();
        return TranslateResDto.of(message);
    }
}
