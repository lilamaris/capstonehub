package com.icnet.capstonehub.application.port.out;

import com.icnet.capstonehub.domain.model.Faculty;

import java.util.List;
import java.util.Optional;

public interface FacultyPort {
    List<Faculty> getAll();
    Optional<Faculty> get(Faculty.Id id);
    Faculty save(Faculty faculty);
}
