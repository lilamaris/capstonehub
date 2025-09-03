package com.icnet.capstonehub.adapter.out.persistence;

import com.icnet.capstonehub.adapter.out.persistence.entity.CollegeEntity;
import com.icnet.capstonehub.adapter.out.persistence.entity.MajorEntity;
import com.icnet.capstonehub.adapter.out.persistence.entity.join.AffiliationEntity;
import com.icnet.capstonehub.adapter.out.persistence.mapper.AffiliationEntityMapper;
import com.icnet.capstonehub.adapter.out.persistence.repository.AffiliationRepository;
import com.icnet.capstonehub.adapter.out.persistence.repository.CollegeRepository;
import com.icnet.capstonehub.adapter.out.persistence.repository.MajorRepository;
import com.icnet.capstonehub.application.port.out.AffiliationPort;
import com.icnet.capstonehub.domain.Affiliation;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class AffiliationPersistenceAdapter implements AffiliationPort {
    private final AffiliationRepository affiliationRepository;
    private final AffiliationEntityMapper affiliationMapper;

    private final CollegeRepository collegeRepository;
    private final MajorRepository majorRepository;

    @Override
    public Affiliation assignMajorToCollege(Affiliation inDomain) {
        CollegeEntity collegeEntity = collegeRepository.getReferenceById(inDomain.collegeId().value());
        MajorEntity majorEntity = majorRepository.getReferenceById(inDomain.majorId().value());

        AffiliationEntity entity = affiliationMapper.toEntity(inDomain, collegeEntity, majorEntity);
        return affiliationMapper.toDomain(affiliationRepository.save(entity));
    }

    @Override
    public void rejectMajorToCollege(Affiliation inDomain) {
        affiliationRepository.findById(inDomain.id().value()).ifPresentOrElse(
                affiliationRepository::delete,
                EntityNotFoundException::new
        );
    }
}
