package com.icnet.capstonehub.application.port.out;

import com.icnet.capstonehub.domain.model.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentPort {
    List<Department> getAll();
    List<Department> getAll(List<Department.Id> ids);
    Optional<Department> get(Department.Id id);
    Department save(Department department);
}