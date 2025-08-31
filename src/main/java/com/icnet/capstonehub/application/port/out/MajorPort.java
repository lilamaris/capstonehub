package com.icnet.capstonehub.application.port.out;

import com.icnet.capstonehub.domain.Major;

import java.util.List;
import java.util.Optional;

public interface MajorPort {
    Major create(Major college);
    List<Major> findAll();
    Optional<Major> findById(Long id);
    Optional<Major> update(Long id, Major college);
    void delete(Long id);
}
