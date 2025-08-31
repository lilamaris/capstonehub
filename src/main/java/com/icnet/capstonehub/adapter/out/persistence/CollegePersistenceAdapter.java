package com.icnet.capstonehub.adapter.out.persistence;

import com.icnet.capstonehub.adapter.out.persistence.entity.CollegeEntity;
import com.icnet.capstonehub.adapter.out.persistence.repository.CollegeRepository;
import com.icnet.capstonehub.application.port.out.CollegePort;
import com.icnet.capstonehub.domain.College;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
class CollegePersistenceAdapter implements CollegePort {
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

    @Override
    public Optional<College> update(Long id, College college) {
        Optional<CollegeEntity> found = collegeRepository.findById(id);
        if (found.isEmpty()) return Optional.empty();

        CollegeEntity entity = found.get();

        entity.setName(college.name());
        entity.setEffectiveStartDate(college.effectiveStartDate());
        entity.setEffectiveEndDate(college.effectiveEndDate());
        collegeRepository.save(entity);

        return Optional.of(College.builder()
                .id(new College.CollegeId(entity.getId()))
                .name(entity.getName())
                .effectiveStartDate(entity.getEffectiveStartDate())
                .effectiveEndDate(entity.getEffectiveEndDate())
                .build());
    }

    @Override
    public void delete(Long id) {
        collegeRepository.deleteById(id);
    }

    @Override
    public Optional<College> findById(Long id) {
        Optional<CollegeEntity> found = collegeRepository.findById(id);
        if (found.isEmpty()) return Optional.empty();
        CollegeEntity entity = found.get();

        return Optional.of(College.builder()
                .id(new College.CollegeId(entity.getId()))
                .name(entity.getName())
                .effectiveStartDate(entity.getEffectiveStartDate())
                .effectiveEndDate(entity.getEffectiveEndDate())
                .build());
    }

    @Override
    public List<College> findAll() {
        List<CollegeEntity> entities = collegeRepository.findAll();

        return entities.stream()
                .map(collegeEntity -> College.builder()
                    .id(new College.CollegeId(collegeEntity.getId()))
                    .name(collegeEntity.getName())
                    .effectiveStartDate(collegeEntity.getEffectiveStartDate())
                    .effectiveEndDate(collegeEntity.getEffectiveEndDate())
                    .build()).toList();
    }
}
