package com.icnet.capstonehub.adapter.out.persistence;

import com.icnet.capstonehub.adapter.out.persistence.entity.LectureEntity;
import com.icnet.capstonehub.adapter.out.persistence.repository.LectureRepository;
import com.icnet.capstonehub.application.port.out.LecturePort;
import com.icnet.capstonehub.domain.Lecture;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
class LecturePersistenceAdapter implements LecturePort {
    private final LectureRepository lectureRepository;

    @Override
    public Lecture create(Lecture lecture) {
        LectureEntity entity = LectureEntity.builder()
                .name(lecture.name())
                .effectiveStartDate(lecture.effectiveStartDate())
                .effectiveEndDate(lecture.effectiveEndDate())
                .build();

        LectureEntity createdEntity = lectureRepository.save(entity);

        return Lecture.builder()
                .id(new Lecture.LectureId(createdEntity.getId()))
                .name(createdEntity.getName())
                .effectiveStartDate(createdEntity.getEffectiveStartDate())
                .effectiveEndDate(createdEntity.getEffectiveEndDate())
                .build();
    }

    @Override
    public Optional<Lecture> update(Long id, Lecture lecture) {
        Optional<LectureEntity> found = lectureRepository.findById(id);
        if (found.isEmpty()) return Optional.empty();

        LectureEntity entity = found.get();

        entity.setName(lecture.name());
        entity.setEffectiveStartDate(lecture.effectiveStartDate());
        entity.setEffectiveEndDate(lecture.effectiveEndDate());
        lectureRepository.save(entity);

        return Optional.of(Lecture.builder()
                .id(new Lecture.LectureId(entity.getId()))
                .name(entity.getName())
                .effectiveStartDate(entity.getEffectiveStartDate())
                .effectiveEndDate(entity.getEffectiveEndDate())
                .build());
    }

    @Override
    public void delete(Long id) {
        lectureRepository.deleteById(id);
    }

    @Override
    public Optional<Lecture> findById(Long id) {
        Optional<LectureEntity> found = lectureRepository.findById(id);
        if (found.isEmpty()) return Optional.empty();
        LectureEntity entity = found.get();

        return Optional.of(Lecture.builder()
                .id(new Lecture.LectureId(entity.getId()))
                .name(entity.getName())
                .effectiveStartDate(entity.getEffectiveStartDate())
                .effectiveEndDate(entity.getEffectiveEndDate())
                .build());
    }

    @Override
    public List<Lecture> findAll() {
        List<LectureEntity> entities = lectureRepository.findAll();

        return entities.stream()
                .map(lectureEntity -> Lecture.builder()
                    .id(new Lecture.LectureId(lectureEntity.getId()))
                    .name(lectureEntity.getName())
                    .effectiveStartDate(lectureEntity.getEffectiveStartDate())
                    .effectiveEndDate(lectureEntity.getEffectiveEndDate())
                    .build()).toList();
    }
}
