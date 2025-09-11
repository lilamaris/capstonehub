package com.icnet.capstonehub.application.port.in.mapper;

import com.icnet.capstonehub.application.port.in.result.VersionResult;
import com.icnet.capstonehub.domain.model.Version;

public class VersionResultMapper {
    public static VersionResult toResult(Version domain) {
        return VersionResult.builder()
                .id(domain.id().value())
                .sharedId(domain.sharedId().value())
                .txFrom(domain.txPeriod().from())
                .txTo(domain.txPeriod().to())
                .versionNo(domain.versionNo())
                .versionDescription(domain.versionDescription())
                .build();
    }
}
