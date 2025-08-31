package com.icnet.capstonehub.application.service;

import com.icnet.capstonehub.application.port.in.CreateMajorUseCase;
import com.icnet.capstonehub.application.port.in.command.CreateMajorCommand;
import com.icnet.capstonehub.application.port.in.response.MajorResponse;
import com.icnet.capstonehub.application.port.out.MajorPort;
import com.icnet.capstonehub.domain.Major;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
class CreateMajorService implements CreateMajorUseCase {
    private final MajorPort majorPort;

    @Override
    public MajorResponse create(CreateMajorCommand command) {
        Major major = Major.builder().
                name(command.name())
                .effectiveStartDate(command.effectiveStartDate())
                .effectiveEndDate(command.effectiveEndDate())
                .build();

        Major createdMajor = majorPort.create(major);

        return MajorResponse.builder()
                .id(createdMajor.id().value())
                .name(createdMajor.name())
                .effectiveStartDate(createdMajor.effectiveStartDate())
                .effectiveEndDate(createdMajor.effectiveEndDate())
                .build();
    }
}
