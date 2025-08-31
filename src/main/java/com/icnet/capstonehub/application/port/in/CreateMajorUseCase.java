package com.icnet.capstonehub.application.port.in;

import com.icnet.capstonehub.application.port.in.command.CreateMajorCommand;
import com.icnet.capstonehub.application.port.in.response.MajorResponse;

public interface CreateMajorUseCase {
    MajorResponse create(CreateMajorCommand command);
}
