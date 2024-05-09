package com.kusitms29.backendH.domain.sync.application.controller.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GraphElement {
    private String name;
    private int percent;
    public static GraphElement of(String name, int percent){
        return GraphElement.builder()
                .name(name)
                .percent(percent)
                .build();
    }
}
