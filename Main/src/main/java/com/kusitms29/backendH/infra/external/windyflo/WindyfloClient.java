package com.kusitms29.backendH.infra.external.windyflo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.kusitms29.backendH.api.translate.service.dto.TranslateReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class WindyfloClient {
    private final String AI_TRANSLATE_URL ="https://windyflo.com/api/v1/prediction/46a6c57e-9971-4e59-9745-1de6777caba4";
    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public JsonNode translateMessage(TranslateReqDto translateReqDto){
        ObjectNode requestBody = objectMapper.createObjectNode();
        requestBody.put("language", translateReqDto.language());
        requestBody.put("type", translateReqDto.type());
        requestBody.put("message", translateReqDto.message());
        QuestionRequest questionRequest = new QuestionRequest(requestBody.toString());

        return webClient.post()
                .uri(AI_TRANSLATE_URL)
                .bodyValue(questionRequest)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
    }
}
