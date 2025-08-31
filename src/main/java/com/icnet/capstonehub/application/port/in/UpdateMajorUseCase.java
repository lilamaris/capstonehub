package com.icnet.capstonehub.application.port.in;

import com.icnet.capstonehub.application.port.in.command.UpdateMajorCommand;
import com.icnet.capstonehub.application.port.in.response.MajorResponse;

public interface UpdateMajorUseCase {
    MajorResponse update(UpdateMajorCommand command);
}
