package com.icnet.capstonehub.application.service;

import com.icnet.capstonehub.application.port.in.FindLectureUseCase;
import com.icnet.capstonehub.application.port.in.response.LectureResponse;
import com.icnet.capstonehub.application.port.out.LecturePort;
import com.icnet.capstonehub.domain.Lecture;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
class FindLectureService implements FindLectureUseCase {
    private final LecturePort lecturePort;

    @Override
    public LectureResponse byId(Long id) {
        Lecture found = lecturePort.findById(id).orElseThrow(Exception::new);

        return LectureResponse.builder()
                .id(found.id().value())
                .name(found.name())
                .effectiveStartDate(found.effectiveStartDate())
                .effectiveEndDate(found.effectiveEndDate())
                .build();
    }
}
