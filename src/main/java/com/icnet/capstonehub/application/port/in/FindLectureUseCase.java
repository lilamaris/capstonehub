package com.icnet.capstonehub.application.port.in;

import com.icnet.capstonehub.application.port.in.response.LectureResponse;

import java.util.Optional;

public interface FindLectureUseCase {
    Optional<LectureResponse> byId(Long id);
}
