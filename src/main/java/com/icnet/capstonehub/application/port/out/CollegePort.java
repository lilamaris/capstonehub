package com.icnet.capstonehub.application.port.out;

import com.icnet.capstonehub.domain.College;

import java.util.List;
import java.util.Optional;

public interface CollegePort {
    College create(College college);
    List<College> findAll();
    Optional<College> findById(Long id);
    Optional<College> update(Long id, College college);
    void delete(Long id);
}
