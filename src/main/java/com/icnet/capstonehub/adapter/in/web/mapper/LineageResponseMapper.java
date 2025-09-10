package com.icnet.capstonehub.adapter.in.web.mapper;

import com.icnet.capstonehub.adapter.in.web.response.LineageResponse;
import com.icnet.capstonehub.application.port.in.result.LineageResult;

public class LineageResponseMapper {
    public static LineageResponse toResponse(LineageResult result) {
        return LineageResponse.builder()
                .id(result.id())
                .sharedId(result.sharedId())
                .scope(result.scope())
                .validFrom(result.validFrom())
                .validTo(result.validTo())
                .build();
    }
}
