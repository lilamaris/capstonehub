package com.icnet.capstonehub.application.service;

import com.icnet.capstonehub.application.port.in.FindCollegeUseCase;
import com.icnet.capstonehub.application.port.in.command.CreateCollegeCommand;
import com.icnet.capstonehub.application.port.in.command.UpdateCollegeCommand;
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
class FindCollegeService implements FindCollegeUseCase {
    private final CollegePort collegePort;

    @Override
    public CollegeResponse byId(Long id) throws Exception {
        College found = collegePort.findById(id).orElseThrow(Exception::new);

        return CollegeResponse.builder()
                .id(found.id().value())
                .name(found.name())
                .effectiveStartDate(found.effectiveStartDate())
                .effectiveEndDate(found.effectiveEndDate())
                .build();
    }
}
