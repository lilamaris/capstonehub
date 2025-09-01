package com.icnet.capstonehub.application.service;

import com.icnet.capstonehub.application.port.in.ManageLectureUseCase;
import com.icnet.capstonehub.application.port.in.command.CreateLectureCommand;
import com.icnet.capstonehub.application.port.in.command.UpdateLectureCommand;
import com.icnet.capstonehub.application.port.in.response.LectureResponse;
import com.icnet.capstonehub.application.port.out.LecturePort;
import com.icnet.capstonehub.domain.Lecture;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
class ManageLectureService implements ManageLectureUseCase {
    private final LecturePort lecturePort;

    @Override
    public List<LectureResponse> findAll() {
        List<Lecture> found = lecturePort.findAll();

        return found.stream()
                .map(lecture -> LectureResponse.builder()
                        .id(lecture.id().value())
                        .name(lecture.name())
                        .effectiveStartDate(lecture.effectiveStartDate())
                        .effectiveEndDate(lecture.effectiveEndDate())
                        .build()).toList();
    }

    @Override
    public Optional<LectureResponse> findById(Long id) {
        return lecturePort.findById(id)
                .map(lecture -> LectureResponse.builder()
                        .id(lecture.id().value())
                        .name(lecture.name())
                        .effectiveStartDate(lecture.effectiveStartDate())
                        .effectiveEndDate(lecture.effectiveEndDate())
                        .build());
    }

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

    @Override
    public void deleteById(Long id) {
        lecturePort.delete(id);
    }
}
