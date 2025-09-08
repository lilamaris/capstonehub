package com.icnet.capstonehub.adapter.in.web.mapper;

import com.icnet.capstonehub.adapter.in.web.response.MajorResponse;
import com.icnet.capstonehub.application.port.in.result.MajorResult;

public class MajorResponseMapper {
    public static MajorResponse toResponse(MajorResult result) {
        return MajorResponse.builder()
                .id(result.id())
                .name(result.name())
                .build();
    }
}
