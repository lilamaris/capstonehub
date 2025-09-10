package com.icnet.capstonehub.adapter.in.web.mapper;

import com.icnet.capstonehub.adapter.in.web.response.VersionResponse;
import com.icnet.capstonehub.application.port.in.result.VersionResult;

public class VersionResponseMapper {
    public static VersionResponse toResponse(VersionResult result) {
        return VersionResponse.builder()
                .id(result.id())
                .sharedId(result.sharedId())
                .versionNo(result.versionNo())
                .versionDescription(result.versionDescription())
                .txFrom(result.txFrom())
                .txTo(result.txTo())
                .build();
    }
}
