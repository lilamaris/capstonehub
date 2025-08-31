package com.icnet.capstonehub.application.port.in;

import com.icnet.capstonehub.application.port.in.command.UpdateLectureCommand;
import com.icnet.capstonehub.application.port.in.response.LectureResponse;

import java.util.Optional;

public interface UpdateLectureUseCase {
    Optional<LectureResponse> update(UpdateLectureCommand command);
}
