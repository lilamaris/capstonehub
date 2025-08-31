package com.icnet.capstonehub.application.service;

import com.icnet.capstonehub.application.port.in.FindMajorUseCase;
import com.icnet.capstonehub.application.port.in.response.MajorResponse;
import com.icnet.capstonehub.application.port.out.MajorPort;
import com.icnet.capstonehub.domain.Major;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
class FindMajorService implements FindMajorUseCase {
    private final MajorPort majorPort;

    @Override
    public MajorResponse byId(Long id) {
        Major found = majorPort.findById(id).orElseThrow(Exception::new);

        return MajorResponse.builder()
                .id(found.id().value())
                .name(found.name())
                .effectiveStartDate(found.effectiveStartDate())
                .effectiveEndDate(found.effectiveEndDate())
                .build();
    }
}
