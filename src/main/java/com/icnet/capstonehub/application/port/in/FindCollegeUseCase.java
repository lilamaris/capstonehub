package com.icnet.capstonehub.application.port.in;

import com.icnet.capstonehub.application.port.in.response.CollegeResponse;

import java.util.Optional;

public interface FindCollegeUseCase {
    Optional<CollegeResponse> byId(Long id);
}
