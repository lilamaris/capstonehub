package com.icnet.capstonehub.application.port.in;

import com.icnet.capstonehub.application.port.in.command.DepartmentCreateCommand;
import com.icnet.capstonehub.application.port.in.result.DepartmentResult;

import java.util.List;

public interface DepartmentUseCase {
    List<DepartmentResult> getAll();
    DepartmentResult createDepartment(DepartmentCreateCommand command);
}

