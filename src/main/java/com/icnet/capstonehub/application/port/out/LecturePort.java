package com.icnet.capstonehub.application.port.out;

import com.icnet.capstonehub.domain.Lecture;

import java.util.List;
import java.util.Optional;

public interface LecturePort {
    Lecture create(Lecture lecture);
    List<Lecture> findAll();
    Optional<Lecture> findById(Long id);
    Optional<Lecture> update(Long id, Lecture lecture);
    void delete(Long id);
}
