package com.icnet.capstonehub.adapter.out.persistence.repository;

import com.icnet.capstonehub.adapter.out.persistence.entity.DepartmentEntity;
import com.icnet.capstonehub.adapter.out.persistence.mapper.DepartmentEntityMapper;
import com.icnet.capstonehub.application.port.out.DepartmentPort;
import com.icnet.capstonehub.domain.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, UUID>, DepartmentPort {
    @Override
    default List<Department> getAll() {
        return findAll().stream().map(DepartmentEntityMapper::toDomain).toList();
    }
    @Override
    default Optional<Department> get(Department.Id id) {
        return findById(id.value()).map(DepartmentEntityMapper::toDomain);
    }

    @Override
    default Department save(Department department) {
        return DepartmentEntityMapper.toDomain(save(DepartmentEntityMapper.toEntity(department)));
    }
}
