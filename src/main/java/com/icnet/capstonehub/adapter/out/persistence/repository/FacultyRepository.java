package com.icnet.capstonehub.adapter.out.persistence.repository;

import com.icnet.capstonehub.adapter.out.persistence.entity.FacultyEntity;
import com.icnet.capstonehub.adapter.out.persistence.mapper.FacultyEntityMapper;
import com.icnet.capstonehub.application.port.out.FacultyPort;
import com.icnet.capstonehub.domain.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FacultyRepository extends JpaRepository<FacultyEntity, UUID>, FacultyPort {
    @Override
    default List<Faculty> getAll() {
        return findAll().stream().map(FacultyEntityMapper::toDomain).toList();
    }
    @Override
    default Optional<Faculty> get(Faculty.Id id) {
        return findById(id.value()).map(FacultyEntityMapper::toDomain);
    }

    @Override
    default Faculty save(Faculty faculty) {
        return FacultyEntityMapper.toDomain(save(FacultyEntityMapper.toEntity(faculty)));
    }
}
