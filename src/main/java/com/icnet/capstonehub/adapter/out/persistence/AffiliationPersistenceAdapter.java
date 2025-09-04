package com.icnet.capstonehub.adapter.out.persistence;

import com.icnet.capstonehub.adapter.out.persistence.entity.CollegeEntity;
import com.icnet.capstonehub.adapter.out.persistence.entity.MajorEntity;
import com.icnet.capstonehub.adapter.out.persistence.entity.join.AffiliationEntity;
import com.icnet.capstonehub.adapter.out.persistence.mapper.AffiliationEntityMapper;
import com.icnet.capstonehub.adapter.out.persistence.mapper.CollegeEntityMapper;
import com.icnet.capstonehub.adapter.out.persistence.mapper.MajorEntityMapper;
import com.icnet.capstonehub.adapter.out.persistence.repository.AffiliationRepository;
import com.icnet.capstonehub.adapter.out.persistence.repository.CollegeRepository;
import com.icnet.capstonehub.adapter.out.persistence.repository.MajorRepository;
import com.icnet.capstonehub.application.port.out.AffiliationPort;
import com.icnet.capstonehub.domain.Affiliation;
import com.icnet.capstonehub.domain.common.EffectivePeriod;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class AffiliationPersistenceAdapter implements AffiliationPort {
    private final AffiliationRepository affiliationRepository;
    private final AffiliationEntityMapper affiliationMapper;

    private final CollegeRepository collegeRepository;
    private final CollegeEntityMapper collegeMapper;
    private final MajorRepository majorRepository;
    private final MajorEntityMapper majorMapper;

    @Override
    public Affiliation assignMajorToCollege(Long majorId, Long collegeId, EffectivePeriod period) {
        CollegeEntity collegeEntity = collegeRepository.getReferenceById(majorId);
        MajorEntity majorEntity = majorRepository.getReferenceById(collegeId);

        AffiliationEntity entity = affiliationRepository.save(affiliationMapper.toEntity(collegeEntity, majorEntity, period));

        return affiliationMapper.toDomain(
                entity,
                collegeMapper.toDomain(entity.getCollege()),
                majorMapper.toDomain(entity.getMajor())
        );
    }

    @Override
    public void rejectMajorToCollege(Long majorId) {
        affiliationRepository.deleteByMajorId(majorId);
    }
}
