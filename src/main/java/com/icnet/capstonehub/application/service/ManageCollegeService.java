package com.icnet.capstonehub.application.service;

import com.icnet.capstonehub.application.port.in.ManageCollegeUseCase;
import com.icnet.capstonehub.application.port.in.command.CreateCollegeCommand;
import com.icnet.capstonehub.application.port.in.command.UpdateCollegeCommand;
import com.icnet.capstonehub.application.port.in.response.CollegeResponse;
import com.icnet.capstonehub.application.port.out.CollegePort;
import com.icnet.capstonehub.domain.College;
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

    @Override
    public List<CollegeResponse> findAll() {
        List<College> found = collegePort.findAll();

        return found.stream()
                .map(college -> CollegeResponse.builder()
                        .id(college.id().value())
                        .name(college.name())
                        .effectiveStartDate(college.effectiveStartDate())
                        .effectiveEndDate(college.effectiveEndDate())
                        .build()).toList();
    }

    @Override
    public Optional<CollegeResponse> findById(Long id) {
        return collegePort.findById(id)
                .map(college -> CollegeResponse.builder()
                        .id(college.id().value())
                        .name(college.name())
                        .effectiveStartDate(college.effectiveStartDate())
                        .effectiveEndDate(college.effectiveEndDate())
                        .build());
    }

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

    @Override
    public void deleteById(Long id) {
        collegePort.delete(id);
    }
}
