package com.icnet.capstonehub.application.port.in;

import com.icnet.capstonehub.application.port.in.command.CreateMajorCommand;
import com.icnet.capstonehub.application.port.in.command.UpdateMajorCommand;
import com.icnet.capstonehub.application.port.in.response.MajorResponse;

import java.util.List;

public interface DeleteMajorUseCase {
    void byId(Long id);
}
