package com.icnet.capstonehub.adapter.out.persistence.mapper;

import com.icnet.capstonehub.adapter.out.persistence.entity.AcademicUnitEntity;
import com.icnet.capstonehub.domain.model.AcademicUnit;

public class AcademicUnitEntityMapper {
    public static AcademicUnit toDomain(AcademicUnitEntity entity) {
        AcademicUnit.Id id = new AcademicUnit.Id(entity.getId());
        return AcademicUnit.builder()
                .id(id)
                .edition(EditionEntityMapper.toDomain(entity.getEdition()))
                .timeline(TimelineEntityMapper.toDomain(entity.getTimeline()))
                .faculty(FacultyEntityMapper.toDomain(entity.getFaculty()))
                .department(DepartmentEntityMapper.toDomain(entity.getDepartment()))
                .build();
    }
}
