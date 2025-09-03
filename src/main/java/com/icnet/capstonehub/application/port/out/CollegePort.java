package com.icnet.capstonehub.application.port.out;

import com.icnet.capstonehub.domain.College;

import java.util.List;
import java.util.Optional;

public interface CollegePort {
    College create(College inDomain);
    College update(Long id, College inDomain);
    List<College> findAll();
    Optional<College> findById(Long id);
    void delete(Long id);
}
