package com.icnet.capstonehub.application.service;

import com.icnet.capstonehub.application.port.in.FindMajorUseCase;
import com.icnet.capstonehub.application.port.in.response.MajorResponse;
import com.icnet.capstonehub.application.port.out.MajorPort;
import com.icnet.capstonehub.domain.Major;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
class FindMajorService implements FindMajorUseCase {
    private final MajorPort majorPort;

    @Override
    public Optional<MajorResponse> byId(Long id) {
        return majorPort.findById(id)
                .map(major -> MajorResponse.builder()
                        .id(major.id().value())
                        .name(major.name())
                        .effectiveStartDate(major.effectiveStartDate())
                        .effectiveEndDate(major.effectiveEndDate())
                        .build());

    }
}
