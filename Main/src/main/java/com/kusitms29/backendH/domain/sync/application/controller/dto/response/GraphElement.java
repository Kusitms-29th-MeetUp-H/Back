package com.kusitms29.backendH.domain.sync.application.controller.dto.response;

public record GraphElement(
        String name,
        int percent
) {
    public static GraphElement of(String name, int percent){
        return new GraphElement(name, percent);
    }
}
