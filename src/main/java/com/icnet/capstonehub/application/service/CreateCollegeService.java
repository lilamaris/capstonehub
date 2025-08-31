package com.icnet.capstonehub.application.service;

import com.icnet.capstonehub.application.port.in.CreateCollegeUseCase;
import com.icnet.capstonehub.application.port.in.command.CreateCollegeCommand;
import com.icnet.capstonehub.application.port.in.response.CreateCollegeResponse;
import com.icnet.capstonehub.application.port.out.CreateCollegePort;
import com.icnet.capstonehub.domain.College;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateCollegeService implements CreateCollegeUseCase {
    private final CreateCollegePort createCollegePort;

    @Override
    public CreateCollegeResponse createCollege(CreateCollegeCommand command) {
        College college = College.builder().
                name(command.name())
                .effectiveStartDate(command.effectiveStartDate())
                .effectiveEndDate(command.effectiveEndDate())
                .build();

        College createdCollege = createCollegePort.create(college);

        return CreateCollegeResponse.builder()
                .id(createdCollege.id().value())
                .name(createdCollege.name())
                .effectiveStartDate(createdCollege.effectiveStartDate())
                .effectiveEndDate(createdCollege.effectiveEndDate())
                .build();
    }
}
