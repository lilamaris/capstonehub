package com.icnet.capstonehub.application.service;

import com.icnet.capstonehub.application.port.in.ManageMajorUseCase;
import com.icnet.capstonehub.application.port.in.command.CreateMajorCommand;
import com.icnet.capstonehub.application.port.in.command.UpdateMajorCommand;
import com.icnet.capstonehub.application.port.out.response.MajorResponse;
import com.icnet.capstonehub.application.port.out.MajorPort;
import com.icnet.capstonehub.application.port.out.mapper.MajorMapper;
import com.icnet.capstonehub.domain.Major;
import com.icnet.capstonehub.domain.common.EffectivePeriod;
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
    private final MajorMapper majorMapper;

    @Override
    public MajorResponse create(CreateMajorCommand command) {
        Major major = Major.builder().
                name(command.name())
                .effective(new EffectivePeriod(command.effectiveStartDate(), command.effectiveEndDate()))
                .build();

        return majorMapper.toResponse(majorPort.create(major));
    }

    @Override
    public MajorResponse update(UpdateMajorCommand command) {
        Major major = Major.builder().
                name(command.name())
                .effective(new EffectivePeriod(command.effectiveStartDate(), command.effectiveEndDate()))
                .build();

        return majorMapper.toResponse(majorPort.update(command.id(), major));
    }

    @Override
    public void deleteById(Long id) {
        majorPort.delete(id);
    }

    @Override
    public List<MajorResponse> findAll() {
        List<Major> majors = majorPort.findAll();
        return majors.stream().map(majorMapper::toResponse).toList();
    }

    @Override
    public Optional<MajorResponse> findById(Long id) {
        return majorPort.findById(id).map(majorMapper::toResponse);
    }
}
