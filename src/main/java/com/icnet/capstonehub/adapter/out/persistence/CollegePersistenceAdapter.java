package com.icnet.capstonehub.adapter.out.persistence;

import com.icnet.capstonehub.adapter.out.persistence.entity.CollegeEntity;
import com.icnet.capstonehub.application.port.out.mapper.CollegeMapper;
import com.icnet.capstonehub.adapter.out.persistence.repository.CollegeRepository;
import com.icnet.capstonehub.application.port.out.CollegePort;
import com.icnet.capstonehub.domain.College;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
class CollegePersistenceAdapter implements CollegePort {
    private final CollegeRepository collegeRepository;
    private final CollegeMapper collegeMapper;

    @Override
    public College create(College inDomain) {
        CollegeEntity entity = collegeMapper.toEntity(inDomain);
        return collegeMapper.toDomain(collegeRepository.save(entity));
    }

    @Override
    public College update(Long id, College inDomain) {
        CollegeEntity entity = collegeRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        entity.setName(inDomain.name());
        entity.setEffectiveStartDate(inDomain.effective().start());
        entity.setEffectiveEndDate(inDomain.effective().end());
        collegeRepository.save(entity);

        return collegeMapper.toDomain(entity);
    }

    @Override
    public void delete(Long id) {
        collegeRepository.deleteById(id);
    }

    @Override
    public Optional<College> findById(Long id) {
        return collegeRepository.findById(id).map(collegeMapper::toDomain);
    }

    @Override
    public List<College> findAll() {
        List<CollegeEntity> entities = collegeRepository.findAll();
        return entities.stream().map(collegeMapper::toDomain).toList();
    }
}
