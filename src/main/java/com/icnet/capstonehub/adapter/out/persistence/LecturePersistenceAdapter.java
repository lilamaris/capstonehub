package com.icnet.capstonehub.adapter.out.persistence;

import com.icnet.capstonehub.adapter.out.persistence.entity.LectureEntity;
import com.icnet.capstonehub.adapter.out.persistence.mapper.LectureEntityMapper;
import com.icnet.capstonehub.adapter.out.persistence.repository.LectureRepository;
import com.icnet.capstonehub.application.port.out.LecturePort;
import com.icnet.capstonehub.domain.Lecture;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
class LecturePersistenceAdapter implements LecturePort {
    private final LectureRepository lectureRepository;
    private final LectureEntityMapper lectureMapper;

    @Override
    public Lecture create(Lecture inDomain) {
        LectureEntity entity = lectureRepository.save(lectureMapper.toEntity(inDomain));
        return lectureMapper.toDomain(entity);
    }

    @Override
    public Lecture update(Long id, Lecture inDomain) {
        LectureEntity entity = lectureRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        entity.setName(inDomain.name());
        entity.setEffectiveStartDate(inDomain.effective().start());
        entity.setEffectiveEndDate(inDomain.effective().end());
        lectureRepository.save(entity);

        return lectureMapper.toDomain(entity);
    }

    @Override
    public void delete(Long id) {
        lectureRepository.deleteById(id);
    }

    @Override
    public Optional<Lecture> findById(Long id) {
        return lectureRepository.findById(id).map(lectureMapper::toDomain);
    }

    @Override
    public List<Lecture> findAll() {
        List<LectureEntity> entities = lectureRepository.findAll();
        return entities.stream().map(lectureMapper::toDomain).toList();
    }
}
