package com.icnet.capstonehub.application.port.in;

import com.icnet.capstonehub.application.port.in.command.CreateMajorCommand;
import com.icnet.capstonehub.application.port.in.command.UpdateMajorCommand;
import com.icnet.capstonehub.application.port.in.response.MajorResponse;

import java.util.List;
import java.util.Optional;

public interface ManageMajorUseCase {
    Optional<MajorResponse> findById(Long id);
    List<MajorResponse> findAll();
    MajorResponse create(CreateMajorCommand command);
    Optional<MajorResponse> update(UpdateMajorCommand command);
    void deleteById(Long id);
}
