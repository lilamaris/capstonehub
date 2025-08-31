package com.icnet.capstonehub.application.service;

import com.icnet.capstonehub.application.port.in.UpdateLectureUseCase;
import com.icnet.capstonehub.application.port.in.command.UpdateLectureCommand;
import com.icnet.capstonehub.application.port.in.response.LectureResponse;
import com.icnet.capstonehub.application.port.out.LecturePort;
import com.icnet.capstonehub.domain.Lecture;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
class UpdateLectureService implements UpdateLectureUseCase {
    private final LecturePort lecturePort;

    @Override
    public Optional<LectureResponse> update(UpdateLectureCommand command) {
        Lecture newLecture = Lecture.builder().
                name(command.name())
                .effectiveStartDate(command.effectiveStartDate())
                .effectiveEndDate(command.effectiveEndDate())
                .build();

        return lecturePort.update(command.id(), newLecture)
                .map(lecture -> LectureResponse.builder()
                        .id(lecture.id().value())
                        .name(lecture.name())
                        .effectiveStartDate(lecture.effectiveStartDate())
                        .effectiveEndDate(lecture.effectiveEndDate())
                        .build());
    }
}
