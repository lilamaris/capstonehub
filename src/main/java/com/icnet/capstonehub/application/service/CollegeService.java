package com.icnet.capstonehub.application.service;

import com.icnet.capstonehub.application.port.in.CollegeUseCase;
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
class CollegeService implements CollegeUseCase {
    private final CollegePort collegePort;

    @Override
    public CollegeResponse createCollege(CreateCollegeCommand command) {
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
    public CollegeResponse updateCollege(UpdateCollegeCommand command) throws Exception {
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

    @Override
    public CollegeResponse findCollegeById(Long id) throws Exception {
        College found = collegePort.findById(id).orElseThrow(Exception::new);

        return CollegeResponse.builder()
                .id(found.id().value())
                .name(found.name())
                .effectiveStartDate(found.effectiveStartDate())
                .effectiveEndDate(found.effectiveEndDate())
                .build();
    }

    @Override
    public List<CollegeResponse> findCollege() {
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
    public void deleteCollege(Long id) {
        collegePort.delete(id);
    }
}
