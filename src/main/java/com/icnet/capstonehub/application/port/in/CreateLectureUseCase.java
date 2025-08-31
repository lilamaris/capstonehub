package com.icnet.capstonehub.application.port.in;

import com.icnet.capstonehub.application.port.in.command.CreateLectureCommand;
import com.icnet.capstonehub.application.port.in.response.LectureResponse;

public interface CreateLectureUseCase {
    LectureResponse create(CreateLectureCommand command);
}
