package com.icnet.capstonehub.application.service;

import com.icnet.capstonehub.application.port.in.ManageCollegeUseCase;
import com.icnet.capstonehub.application.port.in.command.CreateCollegeCommand;
import com.icnet.capstonehub.application.port.in.command.UpdateCollegeCommand;
import com.icnet.capstonehub.application.port.out.response.CollegeResponse;
import com.icnet.capstonehub.application.port.out.CollegePort;
import com.icnet.capstonehub.application.port.out.mapper.CollegeMapper;
import com.icnet.capstonehub.domain.College;
import com.icnet.capstonehub.domain.common.EffectivePeriod;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
class ManageCollegeService implements ManageCollegeUseCase {
    private final CollegePort collegePort;
    private final CollegeMapper collegeMapper;

    @Override
    public CollegeResponse create(CreateCollegeCommand command) {
        College college = College.builder().
                name(command.name())
                .effective(new EffectivePeriod(command.effectiveStartDate(), command.effectiveEndDate()))
                .build();

        return collegeMapper.toResponse(collegePort.create(college));
    }

    @Override
    public CollegeResponse update(UpdateCollegeCommand command) {
        College college = College.builder().
                name(command.name())
                .effective(new EffectivePeriod(command.effectiveStartDate(), command.effectiveEndDate()))
                .build();

        return collegeMapper.toResponse(collegePort.update(command.id(), college));
    }

    @Override
    public void deleteById(Long id) {
        collegePort.delete(id);
    }

    @Override
    public List<CollegeResponse> findAll() {
        List<College> colleges = collegePort.findAll();
        return colleges.stream().map(collegeMapper::toResponse).toList();
    }

    @Override
    public Optional<CollegeResponse> findById(Long id) {
        return collegePort.findById(id).map(collegeMapper::toResponse);
    }
}
