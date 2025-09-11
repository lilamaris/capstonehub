package com.icnet.capstonehub.adapter.out.persistence.repository;

import com.icnet.capstonehub.adapter.out.persistence.entity.CollegeEntity;
import com.icnet.capstonehub.adapter.out.persistence.mapper.CollegeEntityMapper;
import com.icnet.capstonehub.application.port.out.CollegePort;
import com.icnet.capstonehub.domain.model.College;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CollegeRepository extends JpaRepository<CollegeEntity, UUID>, CollegePort {
    @Override
    default List<College> getAll() {
        return findAll().stream().map(CollegeEntityMapper::toDomain).toList();
    }
    @Override
    default Optional<College> get(College.Id id) {
        return findById(id.value()).map(CollegeEntityMapper::toDomain);
    }

    @Override
    default College save(College college) {
        return CollegeEntityMapper.toDomain(save(CollegeEntityMapper.toEntity(college)));
    }
}
