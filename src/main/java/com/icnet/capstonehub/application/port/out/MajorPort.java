package com.icnet.capstonehub.application.port.out;

import com.icnet.capstonehub.domain.model.Major;

import java.util.List;
import java.util.Optional;

public interface MajorPort {
    List<Major> getAll();
    Optional<Major> get(Major.Id id);
    Major save(Major major);
}