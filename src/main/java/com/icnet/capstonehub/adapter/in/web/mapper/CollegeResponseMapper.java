package com.icnet.capstonehub.adapter.in.web.mapper;

import com.icnet.capstonehub.adapter.in.web.response.CollegeResponse;
import com.icnet.capstonehub.application.port.in.result.CollegeResult;

public class CollegeResponseMapper {
    public static CollegeResponse toResponse(CollegeResult result) {
        return CollegeResponse.builder()
                .id(result.id())
                .name(result.name())
                .build();
    }
}
