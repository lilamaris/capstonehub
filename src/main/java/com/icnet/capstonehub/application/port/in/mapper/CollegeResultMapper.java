package com.icnet.capstonehub.application.port.in.mapper;

import com.icnet.capstonehub.application.port.in.result.CollegeResult;
import com.icnet.capstonehub.domain.model.College;
import org.springframework.stereotype.Component;

@Component
public class CollegeResultMapper {
    public static CollegeResult toResult(College domain) {
        if (domain == null) return null;
        return CollegeResult.builder()
                .id(domain.id().value())
                .name(domain.name())
                .build();
    }
}
