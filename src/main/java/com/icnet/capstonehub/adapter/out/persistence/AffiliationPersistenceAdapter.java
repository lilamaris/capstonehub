package com.icnet.capstonehub.adapter.out.persistence;

import com.icnet.capstonehub.adapter.out.persistence.entity.AffiliationEntity;
import com.icnet.capstonehub.adapter.out.persistence.entity.CollegeEntity;
import com.icnet.capstonehub.adapter.out.persistence.entity.MajorEntity;
import com.icnet.capstonehub.adapter.out.persistence.mapper.AffiliationEntityMapper;
import com.icnet.capstonehub.adapter.out.persistence.mapper.LineageEntityMapper;
import com.icnet.capstonehub.adapter.out.persistence.mapper.VersionEntityMapper;
import com.icnet.capstonehub.adapter.out.persistence.repository.AffiliationRepository;
import com.icnet.capstonehub.application.port.out.AffiliationPort;
import com.icnet.capstonehub.domain.model.Affiliation;
import com.icnet.capstonehub.domain.model.Lineage;
import com.icnet.capstonehub.domain.model.Version;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AffiliationPersistenceAdapter implements AffiliationPort {
    private final EntityManager em;
    private final AffiliationRepository affiliationRepository;

    @Override
    public Optional<Affiliation> getSnapshotOfRecord(Lineage.SharedId lineageSharedId, Version.SharedId versionSharedId, LocalDateTime txAt) {
        return affiliationRepository.findSnapshotOfRecord(lineageSharedId.value(), versionSharedId.value(), txAt)
                .map(AffiliationEntityMapper::toDomain);
    }

    @Override
    public Optional<Affiliation> getSnapshotOfRecord(Lineage.SharedId lineageSharedId, LocalDateTime validAt, LocalDateTime txAt) {
        return affiliationRepository.findSnapshotOfRecord(lineageSharedId.value(), validAt, txAt)
                .map(AffiliationEntityMapper::toDomain);
    }

    @Override
    public List<Affiliation> getLineageOfSnapshot(Lineage.SharedId lineageSharedId, LocalDateTime txAt) {
        return affiliationRepository.findLineageOfSnapshot(lineageSharedId.value(), txAt).stream()
                .map(AffiliationEntityMapper::toDomain)
                .toList();
    }

    @Override
    public List<Affiliation> getVersionOfRecord(Lineage.SharedId lineageSharedId, Version.SharedId versionSharedId) {
        return affiliationRepository.findVersionOfRecord(lineageSharedId.value(), versionSharedId.value()).stream()
                .map(AffiliationEntityMapper::toDomain)
                .toList();
    }

    @Override
    public List<Affiliation> getVersionOfRecord(Lineage.SharedId lineageSharedId, LocalDateTime validAt) {
        return affiliationRepository.findVersionOfRecord(lineageSharedId.value(), validAt).stream()
                .map(AffiliationEntityMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public Affiliation save(Affiliation domain) {
        var entity = AffiliationEntity.builder()
                .version(VersionEntityMapper.toEntity(domain.version()))
                .lineage(LineageEntityMapper.toEntity(domain.lineage()))
                .build();

        UUID collegeId = domain.college().id().value();
        UUID majorId = domain.major().id().value();

        entity.setCollege(em.getReference(CollegeEntity.class, collegeId));
        entity.setMajor(em.getReference(MajorEntity.class, majorId));

        var saved = affiliationRepository.save(entity);
        return AffiliationEntityMapper.toDomain(saved);
    }
}
