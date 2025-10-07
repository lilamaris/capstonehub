package com.icnet.capstonehub.application.service;

import com.icnet.capstonehub.application.port.in.DepartmentUseCase;
import com.icnet.capstonehub.application.port.in.command.DepartmentCreateCommand;
import com.icnet.capstonehub.application.port.in.result.DepartmentResult;
import com.icnet.capstonehub.application.port.out.DepartmentPort;
import com.icnet.capstonehub.domain.model.Department;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DepartmentService implements DepartmentUseCase {
    private final DepartmentPort departmentPort;

    @Override
    public List<DepartmentResult> getAll() {
        return departmentPort.getAll().stream().map(DepartmentResult::from).toList();
    }

    @Override
    public DepartmentResult createDepartment(DepartmentCreateCommand command) {
        Department domain = Department.builder().name(command.name()).build();
        return DepartmentResult.from(departmentPort.save(domain));
    }
}
