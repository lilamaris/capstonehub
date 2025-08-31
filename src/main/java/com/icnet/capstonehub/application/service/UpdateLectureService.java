package com.icnet.capstonehub.application.service;

import com.icnet.capstonehub.application.port.in.UpdateLectureUseCase;
import com.icnet.capstonehub.application.port.in.command.UpdateLectureCommand;
import com.icnet.capstonehub.application.port.in.response.LectureResponse;
import com.icnet.capstonehub.application.port.out.LecturePort;
import com.icnet.capstonehub.domain.Lecture;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
class UpdateLectureService implements UpdateLectureUseCase {
    private final LecturePort lecturePort;

    @Override
    public LectureResponse update(UpdateLectureCommand command) {
        Lecture lecture = Lecture.builder().
                name(command.name())
                .effectiveStartDate(command.effectiveStartDate())
                .effectiveEndDate(command.effectiveEndDate())
                .build();

        Lecture updatedLecture = lecturePort.update(command.id(), lecture).orElseThrow(Exception::new);

        return LectureResponse.builder()
                .id(updatedLecture.id().value())
                .name(updatedLecture.name())
                .effectiveStartDate(updatedLecture.effectiveStartDate())
                .effectiveEndDate(updatedLecture.effectiveEndDate())
                .build();
    }
}
