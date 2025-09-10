package com.icnet.capstonehub.application.port.in.mapper;

import com.icnet.capstonehub.application.port.in.result.LineageResult;
import com.icnet.capstonehub.domain.Lineage;

public class LineageResultMapper {
    public static LineageResult toResult(Lineage domain) {
        return LineageResult.builder()
                .id(domain.id().value())
                .sharedId(domain.sharedId().value())
                .scope(domain.scope().name())
                .validFrom(domain.validPeriod().from())
                .validTo(domain.validPeriod().to())
                .build();
    }
}
