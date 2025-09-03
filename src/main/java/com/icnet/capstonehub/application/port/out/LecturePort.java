package com.icnet.capstonehub.application.port.out;

import com.icnet.capstonehub.domain.Lecture;

import java.util.List;
import java.util.Optional;

public interface LecturePort {
    Lecture create(Lecture inDomain);
    Lecture update(Long id, Lecture inDomain);
    List<Lecture> findAll();
    Optional<Lecture> findById(Long id);
    void delete(Long id);
}
