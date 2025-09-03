package com.icnet.capstonehub.application.port.in;

import com.icnet.capstonehub.application.port.in.command.CreateMajorCommand;
import com.icnet.capstonehub.application.port.in.command.UpdateMajorCommand;
import com.icnet.capstonehub.application.port.out.response.MajorResponse;

import java.util.List;
import java.util.Optional;

public interface ManageMajorUseCase {
    MajorResponse create(CreateMajorCommand command);
    MajorResponse update(UpdateMajorCommand command);
    void deleteById(Long id);
    Optional<MajorResponse> findById(Long id);
    List<MajorResponse> findAll();
}
