package com.icnet.capstonehub.application.port.out.mapper;

import com.icnet.capstonehub.application.port.out.response.LectureResponse;
import com.icnet.capstonehub.domain.Lecture;
import org.springframework.stereotype.Component;

@Component
public class LectureMapper {
    public LectureResponse toResponse(Lecture domain) {
        if (domain == null) return null;
        return LectureResponse.builder()
                .id(domain.id().value())
                .name(domain.name())
                .effectiveStartDate(domain.effective().start())
                .effectiveEndDate(domain.effective().end())
                .build();
    }
}
