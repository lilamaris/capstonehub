package com.icnet.capstonehub.application.port.in.mapper;

import com.icnet.capstonehub.application.port.in.result.MajorResult;
import com.icnet.capstonehub.domain.Major;
import org.springframework.stereotype.Component;

@Component
public class MajorResultMapper {
    public static MajorResult toResult(Major domain) {
        if (domain == null) return null;
        return MajorResult.builder()
                .id(domain.id().value())
                .name(domain.name())
                .build();
    }
}
