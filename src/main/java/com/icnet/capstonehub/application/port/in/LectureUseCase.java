package com.icnet.capstonehub.application.port.in;

import com.icnet.capstonehub.application.port.in.command.CreateLectureCommand;
import com.icnet.capstonehub.application.port.in.command.UpdateLectureCommand;
import com.icnet.capstonehub.application.port.in.response.LectureResponse;

import java.util.List;

public interface LectureUseCase {
    LectureResponse createLecture(CreateLectureCommand command);
    LectureResponse updateLecture(UpdateLectureCommand command) throws Exception;
    LectureResponse findLectureById(Long id) throws Exception;
    List<LectureResponse> findLecture();
    void deleteLecture(Long id);
}
