package com.icnet.capstonehub.adapter.out.persistence;

import com.icnet.capstonehub.adapter.out.persistence.entity.MajorEntity;
import com.icnet.capstonehub.adapter.out.persistence.mapper.MajorEntityMapper;
import com.icnet.capstonehub.adapter.out.persistence.repository.MajorRepository;
import com.icnet.capstonehub.application.port.out.MajorPort;
import com.icnet.capstonehub.domain.Major;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
class MajorPersistenceAdapter implements MajorPort {
    private final MajorRepository majorRepository;
    private final MajorEntityMapper majorMapper;

    @Override
    public Major create(Major inDomain) {
        MajorEntity entity = majorRepository.save(majorMapper.toEntity(inDomain));
        return majorMapper.toDomain(entity);
    }

    @Override
    public Major update(Long id, Major inDomain) {
        MajorEntity entity = majorRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        entity.setName(inDomain.name());
        entity.setEffectiveStartDate(inDomain.effective().start());
        entity.setEffectiveEndDate(inDomain.effective().end());
        majorRepository.save(entity);

        return majorMapper.toDomain(entity);
    }

    @Override
    public void delete(Long id) {
        majorRepository.deleteById(id);
    }

    @Override
    public Optional<Major> findById(Long id) {
        return majorRepository.findById(id).map(majorMapper::toDomain);
    }

    @Override
    public List<Major> findAll() {
        List<MajorEntity> entities = majorRepository.findAll();
        return entities.stream().map(majorMapper::toDomain).toList();
    }
}
