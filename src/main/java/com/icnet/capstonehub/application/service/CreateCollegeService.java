package com.icnet.capstonehub.application.service;

import com.icnet.capstonehub.application.port.in.CreateCollegeUseCase;
import com.icnet.capstonehub.application.port.in.command.CreateCollegeCommand;
import com.icnet.capstonehub.application.port.in.response.CollegeResponse;
import com.icnet.capstonehub.application.port.out.CollegePort;
import com.icnet.capstonehub.domain.College;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
class CreateCollegeService implements CreateCollegeUseCase {
    private final CollegePort collegePort;

    @Override
    public CollegeResponse create(CreateCollegeCommand command) {
        College college = College.builder().
                name(command.name())
                .effectiveStartDate(command.effectiveStartDate())
                .effectiveEndDate(command.effectiveEndDate())
                .build();

        College createdCollege = collegePort.create(college);

        return CollegeResponse.builder()
                .id(createdCollege.id().value())
                .name(createdCollege.name())
                .effectiveStartDate(createdCollege.effectiveStartDate())
                .effectiveEndDate(createdCollege.effectiveEndDate())
                .build();
    }
}
