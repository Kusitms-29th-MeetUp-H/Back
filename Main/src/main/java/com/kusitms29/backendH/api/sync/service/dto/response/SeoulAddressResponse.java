package com.kusitms29.backendH.api.sync.service.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class SeoulAddressResponse {
    private List<SeoulAddressResult> regcodes;

    @Getter
    public static class SeoulAddressResult {
        private String code;
        private String name;
    }
}