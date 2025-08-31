package com.icnet.capstonehub.application.service;

import com.icnet.capstonehub.application.port.in.FindAllLectureUseCase;
import com.icnet.capstonehub.application.port.in.response.LectureResponse;
import com.icnet.capstonehub.application.port.out.LecturePort;
import com.icnet.capstonehub.domain.Lecture;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
class FindAllLectureService implements FindAllLectureUseCase {
    private final LecturePort lecturePort;

    @Override
    public List<LectureResponse> find() {
        List<Lecture> found = lecturePort.findAll();

        return found.stream()
                .map(Lecture -> LectureResponse.builder()
                        .id(Lecture.id().value())
                        .name(Lecture.name())
                        .effectiveStartDate(Lecture.effectiveStartDate())
                        .effectiveEndDate(Lecture.effectiveEndDate())
                        .build()).toList();
    }
}
