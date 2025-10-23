package com.icnet.capstonehub.adapter.out.persistence.repository;

import com.icnet.capstonehub.adapter.out.persistence.entity.DepartmentEntity;
import com.icnet.capstonehub.adapter.out.persistence.mapper.DepartmentEntityMapper;
import com.icnet.capstonehub.application.port.out.DepartmentPort;
import com.icnet.capstonehub.domain.model.Department;
import com.icnet.capstonehub.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, UUID>, DepartmentPort {
    @Override
    default List<Department> getAll() {
        return findAll().stream().map(DepartmentEntityMapper::toDomain).toList();
    }

    @Override
    default List<Department> getAll(List<Department.Id> ids) {
        return findAllById(ids.stream().map(Department.Id::value).toList()).stream().map(DepartmentEntityMapper::toDomain).toList();
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
