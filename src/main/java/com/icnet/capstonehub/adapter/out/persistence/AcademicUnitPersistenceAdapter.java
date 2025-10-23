package com.icnet.capstonehub.adapter.out.persistence;

import com.icnet.capstonehub.adapter.out.persistence.entity.*;
import com.icnet.capstonehub.adapter.out.persistence.mapper.AcademicUnitEntityMapper;
import com.icnet.capstonehub.adapter.out.persistence.mapper.TimelineEntityMapper;
import com.icnet.capstonehub.adapter.out.persistence.mapper.EditionEntityMapper;
import com.icnet.capstonehub.adapter.out.persistence.repository.AcademicUnitRepository;
import com.icnet.capstonehub.application.port.out.AcademicUnitPort;
import com.icnet.capstonehub.domain.model.AcademicUnit;
import com.icnet.capstonehub.domain.model.Period;
import com.icnet.capstonehub.domain.model.Timeline;
import com.icnet.capstonehub.domain.model.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AcademicUnitPersistenceAdapter implements AcademicUnitPort {
    private final EntityManager em;
    private final AcademicUnitRepository academicUnitRepository;

    @Override
    public Optional<AcademicUnit> getById(AcademicUnit.Id id) {
        return academicUnitRepository.findById(id.value()).map(AcademicUnitEntityMapper::toDomain);
    }

    @Override
    public List<AcademicUnit> getAllByUserId(User.Id userId) {
        return academicUnitRepository.findAllByUserId(userId.value()).stream().map(AcademicUnitEntityMapper::toDomain).toList();
    }

    @Override
    public List<AcademicUnit> getTimeline(Timeline.SharedId sharedId) {
        return academicUnitRepository.findTimeline(sharedId.value()).stream().map(AcademicUnitEntityMapper::toDomain).toList();
    }

    @Override
    public Optional<AcademicUnit> getHeadOfTimeline(Timeline.SharedId sharedId) {
        return academicUnitRepository.findHeadOfTimeline(sharedId.value(), Period.MAX_TIME).map(AcademicUnitEntityMapper::toDomain);
    }

    @Override
    public List<AcademicUnit> getSnapshot(Timeline.SharedId sharedId, LocalDateTime txAt) {
        return academicUnitRepository.findSnapshot(sharedId.value(), txAt).stream().map(AcademicUnitEntityMapper::toDomain).toList();
    }

    @Override
    public AcademicUnit save(AcademicUnit domain) {
        var entity = AcademicUnitEntity.builder()
                .timeline(TimelineEntityMapper.toEntity(domain.timeline()))
                .edition(EditionEntityMapper.toEntity(domain.edition()))
                .build();

        UUID facultyId = domain.facultyId().value();
        UUID departmentId = domain.departmentId().value();

        entity.setFaculty(em.getReference(FacultyEntity.class, facultyId));
        entity.setDepartment(em.getReference(DepartmentEntity.class, departmentId));

        var saved = academicUnitRepository.save(entity);
        return AcademicUnitEntityMapper.toDomain(saved);
    }
}
