package com.icnet.capstonehub.application.port.in;

import com.icnet.capstonehub.application.port.in.command.UpdateMajorCommand;
import com.icnet.capstonehub.application.port.in.response.MajorResponse;

import java.util.Optional;

public interface UpdateMajorUseCase {
    Optional<MajorResponse> update(UpdateMajorCommand command);
}
