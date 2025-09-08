package com.icnet.capstonehub.application.port.in.mapper;

import com.icnet.capstonehub.application.port.in.result.VersionResult;
import com.icnet.capstonehub.domain.Version;

public class VersionResultMapper {
    public static VersionResult toResult(Version domain) {
        return VersionResult.builder()
                .id(domain.id().value())
                .lineageId(domain.lineageId().value())
                .lineageScope(domain.lineageScope().name())
                .validFrom(domain.validFrom())
                .validTo(domain.validTo())
                .versionNo(domain.versionNo())
                .versionDescription(domain.versionDescription())
                .build();
    }
}
