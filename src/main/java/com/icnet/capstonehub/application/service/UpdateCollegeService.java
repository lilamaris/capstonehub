package com.icnet.capstonehub.application.service;

import com.icnet.capstonehub.application.port.in.UpdateCollegeUseCase;
import com.icnet.capstonehub.application.port.in.command.UpdateCollegeCommand;
import com.icnet.capstonehub.application.port.in.response.CollegeResponse;
import com.icnet.capstonehub.application.port.out.CollegePort;
import com.icnet.capstonehub.domain.College;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
class UpdateCollegeService implements UpdateCollegeUseCase {
    private final CollegePort collegePort;

    @Override
    public Optional<CollegeResponse> update(UpdateCollegeCommand command) {
        College newCollege = College.builder().
                name(command.name())
                .effectiveStartDate(command.effectiveStartDate())
                .effectiveEndDate(command.effectiveEndDate())
                .build();

        return collegePort.update(command.id(), newCollege)
                .map(college -> CollegeResponse.builder()
                        .id(college.id().value())
                        .name(college.name())
                        .effectiveStartDate(college.effectiveStartDate())
                        .effectiveEndDate(college.effectiveEndDate())
                        .build());
    }
}
