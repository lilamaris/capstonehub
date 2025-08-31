package com.icnet.capstonehub.application.service;

import com.icnet.capstonehub.application.port.in.UpdateMajorUseCase;
import com.icnet.capstonehub.application.port.in.command.UpdateMajorCommand;
import com.icnet.capstonehub.application.port.in.response.MajorResponse;
import com.icnet.capstonehub.application.port.out.MajorPort;
import com.icnet.capstonehub.domain.Major;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
class UpdateMajorService implements UpdateMajorUseCase {
    private final MajorPort majorPort;

    @Override
    public MajorResponse update(UpdateMajorCommand command) {
        Major major = Major.builder().
                name(command.name())
                .effectiveStartDate(command.effectiveStartDate())
                .effectiveEndDate(command.effectiveEndDate())
                .build();

        Major updatedMajor = majorPort.update(command.id(), major).orElseThrow(Exception::new);

        return MajorResponse.builder()
                .id(updatedMajor.id().value())
                .name(updatedMajor.name())
                .effectiveStartDate(updatedMajor.effectiveStartDate())
                .effectiveEndDate(updatedMajor.effectiveEndDate())
                .build();
    }
}
