package com.icnet.capstonehub.application.service;

import com.icnet.capstonehub.application.port.in.MajorUseCase;
import com.icnet.capstonehub.application.port.in.command.CreateMajorCommand;
import com.icnet.capstonehub.application.port.in.command.UpdateMajorCommand;
import com.icnet.capstonehub.application.port.in.response.MajorResponse;
import com.icnet.capstonehub.application.port.out.MajorPort;
import com.icnet.capstonehub.domain.Major;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
class MajorService implements MajorUseCase {
    private final MajorPort majorPort;

    @Override
    public MajorResponse createMajor(CreateMajorCommand command) {
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
    public MajorResponse updateMajor(UpdateMajorCommand command) throws Exception {
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

    @Override
    public MajorResponse findMajorById(Long id) throws Exception {
        Major found = majorPort.findById(id).orElseThrow(Exception::new);

        return MajorResponse.builder()
                .id(found.id().value())
                .name(found.name())
                .effectiveStartDate(found.effectiveStartDate())
                .effectiveEndDate(found.effectiveEndDate())
                .build();
    }

    @Override
    public List<MajorResponse> findMajor() {
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
    public void deleteMajor(Long id) {
        majorPort.delete(id);
    }
}
