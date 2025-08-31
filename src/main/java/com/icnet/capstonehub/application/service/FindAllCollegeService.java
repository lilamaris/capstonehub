package com.icnet.capstonehub.application.service;

import com.icnet.capstonehub.application.port.in.FindAllCollegeUseCase;
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
class FindAllCollegeService implements FindAllCollegeUseCase {
    private final CollegePort collegePort;

    @Override
    public List<CollegeResponse> find() {
        List<College> found = collegePort.findAll();

        return found.stream()
                .map(college -> CollegeResponse.builder()
                        .id(college.id().value())
                        .name(college.name())
                        .effectiveStartDate(college.effectiveStartDate())
                        .effectiveEndDate(college.effectiveEndDate())
                        .build()).toList();
    }
}
