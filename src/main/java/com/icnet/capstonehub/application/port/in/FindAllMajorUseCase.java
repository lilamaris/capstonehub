package com.icnet.capstonehub.application.port.in;

import com.icnet.capstonehub.application.port.in.response.MajorResponse;

import java.util.List;

public interface FindAllMajorUseCase {
    List<MajorResponse> find();
}
