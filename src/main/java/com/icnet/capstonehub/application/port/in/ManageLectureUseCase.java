package com.icnet.capstonehub.application.port.in;

import com.icnet.capstonehub.application.port.in.command.CreateLectureCommand;
import com.icnet.capstonehub.application.port.in.command.UpdateLectureCommand;
import com.icnet.capstonehub.application.port.in.response.LectureResponse;

import java.util.List;
import java.util.Optional;

public interface ManageLectureUseCase {
    Optional<LectureResponse> findById(Long id);
    List<LectureResponse> findAll();
    LectureResponse create(CreateLectureCommand command);
    Optional<LectureResponse> update(UpdateLectureCommand command);
    void deleteById(Long id);
}
