package com.icnet.capstonehub.adapter.out.persistence.repository;

import com.icnet.capstonehub.adapter.out.persistence.entity.EditionEntity;
import com.icnet.capstonehub.adapter.out.persistence.mapper.EditionEntityMapper;
import com.icnet.capstonehub.application.port.out.EditionPort;
import com.icnet.capstonehub.domain.model.Edition;
import com.icnet.capstonehub.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EditionRepository extends JpaRepository<EditionEntity, UUID>, EditionPort {
    @Override
    default Edition save(Edition domain) {
        return EditionEntityMapper.toDomain(save(EditionEntityMapper.toEntity(domain)));
    }

    @Override
    default List<Edition> getAll() {
        return findAll().stream()
                .map(EditionEntityMapper::toDomain)
                .toList();
    }

    @Query("""
          SELECT e
          FROM EditionEntity e
          WHERE createdBy = :userId""")
    @Override
    List<Edition> getByUserId(@Param("userId") User.Id userId);
}
