package com.icnet.capstonehub.application.port.in;

import com.icnet.capstonehub.application.port.in.response.LectureResponse;

import java.util.List;

public interface FindAllLectureUseCase {
    List<LectureResponse> find();
}
