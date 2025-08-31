package com.icnet.capstonehub.adapter.out.persistence;

import com.icnet.capstonehub.adapter.out.persistence.entity.MajorEntity;
import com.icnet.capstonehub.adapter.out.persistence.repository.MajorRepository;
import com.icnet.capstonehub.application.port.out.MajorPort;
import com.icnet.capstonehub.domain.Major;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
class MajorPersistenceAdapter implements MajorPort {
    private final MajorRepository majorRepository;

    @Override
    public Major create(Major major) {
        MajorEntity entity = MajorEntity.builder()
                .name(major.name())
                .effectiveStartDate(major.effectiveStartDate())
                .effectiveEndDate(major.effectiveEndDate())
                .build();

        MajorEntity createdEntity = majorRepository.save(entity);

        return Major.builder()
                .id(new Major.MajorId(createdEntity.getId()))
                .name(createdEntity.getName())
                .effectiveStartDate(createdEntity.getEffectiveStartDate())
                .effectiveEndDate(createdEntity.getEffectiveEndDate())
                .build();
    }

    @Override
    public Optional<Major> update(Long id, Major major) {
        Optional<MajorEntity> found = majorRepository.findById(id);
        if (found.isEmpty()) return Optional.empty();

        MajorEntity entity = found.get();

        entity.setName(major.name());
        entity.setEffectiveStartDate(major.effectiveStartDate());
        entity.setEffectiveEndDate(major.effectiveEndDate());
        majorRepository.save(entity);

        return Optional.of(Major.builder()
                .id(new Major.MajorId(entity.getId()))
                .name(entity.getName())
                .effectiveStartDate(entity.getEffectiveStartDate())
                .effectiveEndDate(entity.getEffectiveEndDate())
                .build());
    }

    @Override
    public void delete(Long id) {
        majorRepository.deleteById(id);
    }

    @Override
    public Optional<Major> findById(Long id) {
        Optional<MajorEntity> found = majorRepository.findById(id);
        if (found.isEmpty()) return Optional.empty();
        MajorEntity entity = found.get();

        return Optional.of(Major.builder()
                .id(new Major.MajorId(entity.getId()))
                .name(entity.getName())
                .effectiveStartDate(entity.getEffectiveStartDate())
                .effectiveEndDate(entity.getEffectiveEndDate())
                .build());
    }

    @Override
    public List<Major> findAll() {
        List<MajorEntity> entities = majorRepository.findAll();

        return entities.stream()
                .map(MajorEntity -> Major.builder()
                    .id(new Major.MajorId(MajorEntity.getId()))
                    .name(MajorEntity.getName())
                    .effectiveStartDate(MajorEntity.getEffectiveStartDate())
                    .effectiveEndDate(MajorEntity.getEffectiveEndDate())
                    .build()).toList();
    }
}
