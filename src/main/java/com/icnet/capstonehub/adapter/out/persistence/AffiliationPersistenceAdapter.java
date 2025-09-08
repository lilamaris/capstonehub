package com.icnet.capstonehub.adapter.out.persistence;

import com.icnet.capstonehub.adapter.out.persistence.entity.AffiliationEntity;
import com.icnet.capstonehub.adapter.out.persistence.entity.CollegeEntity;
import com.icnet.capstonehub.adapter.out.persistence.entity.MajorEntity;
import com.icnet.capstonehub.adapter.out.persistence.mapper.AffiliationEntityMapper;
import com.icnet.capstonehub.adapter.out.persistence.mapper.VersionEntityMapper;
import com.icnet.capstonehub.adapter.out.persistence.repository.AffiliationRepository;
import com.icnet.capstonehub.application.port.out.AffiliationPort;
import com.icnet.capstonehub.domain.Affiliation;
import com.icnet.capstonehub.domain.Version;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AffiliationPersistenceAdapter implements AffiliationPort {
    private final EntityManager em;
    private final AffiliationRepository affiliationRepository;

    @Override
    public List<Affiliation> getLineage(Version.LineageId lineageId) {
        return affiliationRepository.findByLineageId(lineageId.value()).stream()
                .map(AffiliationEntityMapper::toDomain).toList();
    }

    @Override
    public Optional<Affiliation> getCurrent(Version.LineageId lineageId) {
        return affiliationRepository.findCurrent(lineageId.value()).map(AffiliationEntityMapper::toDomain);
    }

    @Override
    public Optional<Affiliation> getCurrent(Version.LineageId lineageId, LocalDate current) {
        return affiliationRepository.findCurrent(lineageId.value(), current).map(AffiliationEntityMapper::toDomain);
    }

    @Override
    @Transactional
    public Affiliation save(Affiliation domain) {
        var entity = AffiliationEntity.builder()
                .version(VersionEntityMapper.toEntity(domain.version()))
                .build();

        UUID collegeId = domain.college().id().value();
        UUID majorId = domain.major().id().value();

        entity.setCollege(em.getReference(CollegeEntity.class, collegeId));
        entity.setMajor(em.getReference(MajorEntity.class, majorId));

        var saved = affiliationRepository.save(entity);
        return AffiliationEntityMapper.toDomain(saved);
    }
}
