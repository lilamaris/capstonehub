package com.icnet.capstonehub.application.service;

import com.icnet.capstonehub.application.port.in.ManageMajorUseCase;
import com.icnet.capstonehub.application.port.in.command.CreateMajorCommand;
import com.icnet.capstonehub.application.port.in.command.UpdateMajorCommand;
import com.icnet.capstonehub.application.port.in.response.MajorResponse;
import com.icnet.capstonehub.application.port.out.MajorPort;
import com.icnet.capstonehub.domain.Major;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
class ManageMajorService implements ManageMajorUseCase {
    private final MajorPort majorPort;

    @Override
    public List<MajorResponse> findAll() {
        List<Major> found = majorPort.findAll();

        return found.stream()
                .map(major -> MajorResponse.builder()
                        .id(major.id().value())
                        .name(major.name())
                        .effectiveStartDate(major.effectiveStartDate())
                        .effectiveEndDate(major.effectiveEndDate())
                        .build()).toList();
    }

    @Override
    public Optional<MajorResponse> findById(Long id) {
        return majorPort.findById(id)
                .map(major -> MajorResponse.builder()
                        .id(major.id().value())
                        .name(major.name())
                        .effectiveStartDate(major.effectiveStartDate())
                        .effectiveEndDate(major.effectiveEndDate())
                        .build());
    }

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

    @Override
    public Optional<MajorResponse> update(UpdateMajorCommand command) {
        Major newMajor = Major.builder().
                name(command.name())
                .effectiveStartDate(command.effectiveStartDate())
                .effectiveEndDate(command.effectiveEndDate())
                .build();

        return majorPort.update(command.id(), newMajor)
                .map(major -> MajorResponse.builder()
                        .id(major.id().value())
                        .name(major.name())
                        .effectiveStartDate(major.effectiveStartDate())
                        .effectiveEndDate(major.effectiveEndDate())
                        .build());
    }

    @Override
    public void deleteById(Long id) {
        majorPort.delete(id);
    }
}
