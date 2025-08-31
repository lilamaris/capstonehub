package com.icnet.capstonehub.application.port.in;

import com.icnet.capstonehub.application.port.in.command.CreateLectureCommand;
import com.icnet.capstonehub.application.port.in.command.UpdateLectureCommand;
import com.icnet.capstonehub.application.port.in.response.LectureResponse;

import java.util.List;

public interface DeleteLectureUseCase {
    void byId(Long id);
}
