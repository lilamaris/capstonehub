package com.icnet.capstonehub.application.port.in;

import com.icnet.capstonehub.application.port.in.response.LectureResponse;

public interface FindLectureUseCase {
    LectureResponse byId(Long id);
}
