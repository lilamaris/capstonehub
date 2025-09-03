package com.icnet.capstonehub.application.port.out;

import com.icnet.capstonehub.domain.Major;

import java.util.List;
import java.util.Optional;

public interface MajorPort {
    Major create(Major inDomain);
    Major update(Long id, Major inDomain);
    List<Major> findAll();
    Optional<Major> findById(Long id);
    void delete(Long id);
}
