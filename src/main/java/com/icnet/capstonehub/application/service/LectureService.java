package com.icnet.capstonehub.application.service;

import com.icnet.capstonehub.application.port.in.LectureUseCase;
import com.icnet.capstonehub.application.port.in.command.CreateLectureCommand;
import com.icnet.capstonehub.application.port.in.command.UpdateLectureCommand;
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
class LectureService implements LectureUseCase {
    private final LecturePort lecturePort;

    @Override
    public LectureResponse createLecture(CreateLectureCommand command) {
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
    public LectureResponse updateLecture(UpdateLectureCommand command) throws Exception {
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

    @Override
    public LectureResponse findLectureById(Long id) throws Exception {
        Lecture found = lecturePort.findById(id).orElseThrow(Exception::new);

        return LectureResponse.builder()
                .id(found.id().value())
                .name(found.name())
                .effectiveStartDate(found.effectiveStartDate())
                .effectiveEndDate(found.effectiveEndDate())
                .build();
    }

    @Override
    public List<LectureResponse> findLecture() {
        List<Lecture> found = lecturePort.findAll();

        return found.stream()
                .map(Lecture -> LectureResponse.builder()
                        .id(Lecture.id().value())
                        .name(Lecture.name())
                        .effectiveStartDate(Lecture.effectiveStartDate())
                        .effectiveEndDate(Lecture.effectiveEndDate())
                        .build()).toList();
    }

    @Override
    public void deleteLecture(Long id) {
        lecturePort.delete(id);
    }
}
