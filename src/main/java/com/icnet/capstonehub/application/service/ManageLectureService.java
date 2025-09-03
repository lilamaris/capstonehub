package com.icnet.capstonehub.application.service;

import com.icnet.capstonehub.application.port.in.ManageLectureUseCase;
import com.icnet.capstonehub.application.port.in.command.CreateLectureCommand;
import com.icnet.capstonehub.application.port.in.command.UpdateLectureCommand;
import com.icnet.capstonehub.application.port.in.response.LectureResponse;
import com.icnet.capstonehub.application.port.out.LecturePort;
import com.icnet.capstonehub.application.port.out.mapper.LectureMapper;
import com.icnet.capstonehub.domain.Lecture;
import com.icnet.capstonehub.domain.common.EffectivePeriod;
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
    private final LectureMapper lectureMapper;

    @Override
    public LectureResponse create(CreateLectureCommand command) {
        Lecture lecture = Lecture.builder()
                .name(command.name())
                .effective(new EffectivePeriod(command.effectiveStartDate(), command.effectiveEndDate()))
                .build();

        return lectureMapper.toResponse(lecturePort.create(lecture));
    }

    @Override
    public LectureResponse update(UpdateLectureCommand command) {
        Lecture lecture = Lecture.builder()
                .name(command.name())
                .effective(new EffectivePeriod(command.effectiveStartDate(), command.effectiveEndDate()))
                .build();

        return lectureMapper.toResponse(lecturePort.update(command.id(), lecture));
    }

    @Override
    public void deleteById(Long id) {
        lecturePort.delete(id);
    }

    @Override
    public List<LectureResponse> findAll() {
        List<Lecture> lectures = lecturePort.findAll();

        return lectures.stream().map(lectureMapper::toResponse).toList();
    }

    @Override
    public Optional<LectureResponse> findById(Long id) {
        return lecturePort.findById(id).map(lectureMapper::toResponse);
    }
}
