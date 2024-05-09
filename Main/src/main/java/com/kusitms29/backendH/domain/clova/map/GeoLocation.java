package com.kusitms29.backendH.domain.clova.map;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class GeoLocation {
    private String country;
    private String code;
    private String r1;
    private String r2;
    private String r3;
    private double lat;
//    @JsonProperty("long")
//    private double longitude;
    private String net;

    // Getters and Setters
}
