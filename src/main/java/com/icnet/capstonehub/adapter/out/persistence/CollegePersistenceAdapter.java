package com.icnet.capstonehub.adapter.out.persistence;

import com.icnet.capstonehub.adapter.out.persistence.entity.CollegeEntity;
import com.icnet.capstonehub.adapter.out.persistence.repository.CollegeRepository;
import com.icnet.capstonehub.application.port.out.CreateCollegePort;
import com.icnet.capstonehub.domain.College;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CollegePersistenceAdapter implements CreateCollegePort {
    private final CollegeRepository collegeRepository;

    @Override
    public College create(College college) {
        CollegeEntity entity = CollegeEntity.builder()
                .name(college.name())
                .effectiveStartDate(college.effectiveStartDate())
                .effectiveEndDate(college.effectiveEndDate())
                .build();

        CollegeEntity createdEntity = collegeRepository.save(entity);

        return College.builder()
                .id(new College.CollegeId(createdEntity.getId()))
                .name(createdEntity.getName())
                .effectiveStartDate(createdEntity.getEffectiveStartDate())
                .effectiveEndDate(createdEntity.getEffectiveEndDate())
                .build();
    }
}
