package com.icnet.capstonehub.application.port.out.mapper;

import com.icnet.capstonehub.application.port.out.response.CollegeResponse;
import com.icnet.capstonehub.domain.College;
import org.springframework.stereotype.Component;

@Component
public class CollegeMapper {
    public CollegeResponse toResponse(College domain) {
        if (domain == null) return null;
        return CollegeResponse.builder()
                .id(domain.id().value())
                .name(domain.name())
                .effectiveStartDate(domain.effective().start())
                .effectiveEndDate(domain.effective().end())
                .build();
    }
}
