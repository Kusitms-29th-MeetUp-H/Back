package com.kusitms29.backendH.infra.external.clova.map;

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
