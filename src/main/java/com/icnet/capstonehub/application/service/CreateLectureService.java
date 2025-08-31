package com.icnet.capstonehub.application.service;

import com.icnet.capstonehub.application.port.in.CreateLectureUseCase;
import com.icnet.capstonehub.application.port.in.command.CreateLectureCommand;
import com.icnet.capstonehub.application.port.in.response.LectureResponse;
import com.icnet.capstonehub.application.port.out.LecturePort;
import com.icnet.capstonehub.domain.Lecture;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
class CreateLectureService implements CreateLectureUseCase {
    private final LecturePort lecturePort;

    @Override
    public LectureResponse create(CreateLectureCommand command) {
        Lecture lecture = Lecture.builder().
                name(command.name())
                .effectiveStartDate(command.effectiveStartDate())
                .effectiveEndDate(command.effectiveEndDate())
                .build();

        Lecture createdLecture = lecturePort.create(lecture);

        return LectureResponse.builder()
                .id(createdLecture.id().value())
                .name(createdLecture.name())
                .effectiveStartDate(createdLecture.effectiveStartDate())
                .effectiveEndDate(createdLecture.effectiveEndDate())
                .build();
    }
}
