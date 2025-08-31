package com.icnet.capstonehub.application.service;

import com.icnet.capstonehub.application.port.in.FindLectureUseCase;
import com.icnet.capstonehub.application.port.in.response.LectureResponse;
import com.icnet.capstonehub.application.port.out.LecturePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
class FindLectureService implements FindLectureUseCase {
    private final LecturePort lecturePort;

    @Override
    public Optional<LectureResponse> byId(Long id) {
        return lecturePort.findById(id)
                .map(lecture -> LectureResponse.builder()
                        .id(lecture.id().value())
                        .name(lecture.name())
                        .effectiveStartDate(lecture.effectiveStartDate())
                        .effectiveEndDate(lecture.effectiveEndDate())
                        .build());
    }
}
