package com.icnet.capstonehub.adapter.out.persistence.repository;

import com.icnet.capstonehub.adapter.out.persistence.entity.MajorEntity;
import com.icnet.capstonehub.adapter.out.persistence.mapper.MajorEntityMapper;
import com.icnet.capstonehub.application.port.out.MajorPort;
import com.icnet.capstonehub.domain.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MajorRepository extends JpaRepository<MajorEntity, UUID>, MajorPort {
    @Override
    default List<Major> getAll() {
        return findAll().stream().map(MajorEntityMapper::toDomain).toList();
    }
    @Override
    default Optional<Major> get(Major.Id id) {
        return findById(id.value()).map(MajorEntityMapper::toDomain);
    }

    @Override
    default Major save(Major major) {
        return MajorEntityMapper.toDomain(save(MajorEntityMapper.toEntity(major)));
    }
}
