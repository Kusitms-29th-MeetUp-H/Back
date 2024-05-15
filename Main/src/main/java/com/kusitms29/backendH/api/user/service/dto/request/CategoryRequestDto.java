package com.kusitms29.backendH.api.user.service.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CategoryRequestDto {
    private Map<String, Boolean> foreignLanguage;
    private Map<String, Boolean> cultureArt;
    private Map<String, Boolean> travelCompanion;
    private Map<String, Boolean> activity;
    private Map<String, Boolean> foodAndDrink;
    private Map<String, Boolean> etc;
}
