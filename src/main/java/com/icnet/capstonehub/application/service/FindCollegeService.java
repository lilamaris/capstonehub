package com.icnet.capstonehub.application.service;

import com.icnet.capstonehub.application.port.in.FindCollegeUseCase;
import com.icnet.capstonehub.application.port.in.response.CollegeResponse;
import com.icnet.capstonehub.application.port.out.CollegePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
class FindCollegeService implements FindCollegeUseCase {
    private final CollegePort collegePort;

    @Override
    public Optional<CollegeResponse> byId(Long id) {
        return collegePort.findById(id)
                .map(college -> CollegeResponse.builder()
                        .id(college.id().value())
                        .name(college.name())
                        .effectiveStartDate(college.effectiveStartDate())
                        .effectiveEndDate(college.effectiveEndDate())
                        .build());
    }
}
