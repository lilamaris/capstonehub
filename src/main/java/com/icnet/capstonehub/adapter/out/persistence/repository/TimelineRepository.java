package com.icnet.capstonehub.adapter.out.persistence.repository;

import com.icnet.capstonehub.adapter.out.persistence.entity.TimelineEntity;
import com.icnet.capstonehub.adapter.out.persistence.mapper.TimelineEntityMapper;
import com.icnet.capstonehub.application.port.out.TimelinePort;
import com.icnet.capstonehub.domain.model.Timeline;
import com.icnet.capstonehub.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TimelineRepository extends JpaRepository<TimelineEntity, UUID>, TimelinePort {
    @Override
    default Timeline save(Timeline domain) {
         return TimelineEntityMapper.toDomain(save(TimelineEntityMapper.toEntity(domain)));
    }
    @Override
    default List<Timeline> getAll() {
        return findAll().stream()
                .map(TimelineEntityMapper::toDomain)
                .toList();
    }

    @Query("""
            SELECT t
            FROM TimelineEntity t
            WHERE createdBy = :userId""")
    @Override
    List<Timeline> getByUserId(@Param("userId") User.Id userId);
}
