package com.icnet.capstonehub.application.port.in;

import com.icnet.capstonehub.application.port.in.response.MajorResponse;

import java.util.Optional;

public interface FindMajorUseCase {
    Optional<MajorResponse> byId(Long id);
}
