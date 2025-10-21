package com.icnet.capstonehub.adapter.out.persistence.mapper;

import com.icnet.capstonehub.adapter.out.persistence.entity.AcademicUnitEntity;
import com.icnet.capstonehub.domain.model.AcademicUnit;
import com.icnet.capstonehub.domain.model.Department;
import com.icnet.capstonehub.domain.model.Faculty;

public class AcademicUnitEntityMapper {
    public static AcademicUnit toDomain(AcademicUnitEntity entity) {
        AcademicUnit.Id id = new AcademicUnit.Id(entity.getId());
        Faculty.Id facultyId = new Faculty.Id(entity.getFaculty().getId());
        Department.Id departmentId = new Department.Id(entity.getDepartment().getId());

        return AcademicUnit.builder()
                .id(id)
                .edition(EditionEntityMapper.toDomain(entity.getEdition()))
                .timeline(TimelineEntityMapper.toDomain(entity.getTimeline()))
                .facultyId(facultyId)
                .departmentId(departmentId)
                .build();
    }
}
