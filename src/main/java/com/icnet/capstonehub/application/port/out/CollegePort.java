package com.icnet.capstonehub.application.port.out;

import com.icnet.capstonehub.domain.model.College;

import java.util.List;
import java.util.Optional;

public interface CollegePort {
    List<College> getAll();
    Optional<College> get(College.Id id);
    College save(College college);
}
