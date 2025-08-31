package com.icnet.capstonehub.application.service;

import com.icnet.capstonehub.application.port.in.UpdateMajorUseCase;
import com.icnet.capstonehub.application.port.in.command.UpdateMajorCommand;
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
class UpdateMajorService implements UpdateMajorUseCase {
    private final MajorPort majorPort;

    @Override
    public Optional<MajorResponse> update(UpdateMajorCommand command) {
        Major newMajor = Major.builder().
                name(command.name())
                .effectiveStartDate(command.effectiveStartDate())
                .effectiveEndDate(command.effectiveEndDate())
                .build();

        return majorPort.update(command.id(), newMajor)
                .map(major ->MajorResponse.builder()
                        .id(major.id().value())
                        .name(major.name())
                        .effectiveStartDate(major.effectiveStartDate())
                        .effectiveEndDate(major.effectiveEndDate())
                        .build());
    }
}
