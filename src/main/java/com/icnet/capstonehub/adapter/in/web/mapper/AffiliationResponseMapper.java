package com.icnet.capstonehub.adapter.in.web.mapper;

import com.icnet.capstonehub.adapter.in.web.response.AffiliationResponse;
import com.icnet.capstonehub.application.port.in.result.AffiliationResult;

public class AffiliationResponseMapper {
    public static AffiliationResponse toResponse(AffiliationResult result) {
        return AffiliationResponse.builder()
                .id(result.id())
                .college(CollegeResponseMapper.toResponse(result.college()))
                .major(MajorResponseMapper.toResponse(result.major()))
                .version(VersionResponseMapper.toResponse(result.version()))
                .build();
    }
}
