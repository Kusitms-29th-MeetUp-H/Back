package com.kusitms29.backendH.infra.external.clova.map;

import lombok.Getter;

@Getter
public class GeoLocationResponse{
    private int returnCode;
    private String requestId;
    private GeoLocation geoLocation;
}
