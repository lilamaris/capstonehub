package com.icnet.capstonehub.application.service;

import com.icnet.capstonehub.application.port.in.UpdateCollegeUseCase;
import com.icnet.capstonehub.application.port.in.command.UpdateCollegeCommand;
import com.icnet.capstonehub.application.port.in.response.CollegeResponse;
import com.icnet.capstonehub.application.port.out.CollegePort;
import com.icnet.capstonehub.domain.College;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
class UpdateCollegeService implements UpdateCollegeUseCase {
    private final CollegePort collegePort;

    @Override
    public CollegeResponse update(UpdateCollegeCommand command) {
        College college = College.builder().
                name(command.name())
                .effectiveStartDate(command.effectiveStartDate())
                .effectiveEndDate(command.effectiveEndDate())
                .build();

        College updatedCollege = collegePort.update(command.id(), college).orElseThrow(Exception::new);

        return CollegeResponse.builder()
                .id(updatedCollege.id().value())
                .name(updatedCollege.name())
                .effectiveStartDate(updatedCollege.effectiveStartDate())
                .effectiveEndDate(updatedCollege.effectiveEndDate())
                .build();
    }
}
