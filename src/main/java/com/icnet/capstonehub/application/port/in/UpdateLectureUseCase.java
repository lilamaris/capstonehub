package com.icnet.capstonehub.application.port.in;

import com.icnet.capstonehub.application.port.in.command.UpdateLectureCommand;
import com.icnet.capstonehub.application.port.in.response.LectureResponse;

public interface UpdateLectureUseCase {
    LectureResponse update(UpdateLectureCommand command);
}
