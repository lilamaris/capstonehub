package com.icnet.capstonehub.application.port.in;

import com.icnet.capstonehub.application.port.in.command.UpdateCollegeCommand;
import com.icnet.capstonehub.application.port.in.response.CollegeResponse;

import java.util.Optional;

public interface UpdateCollegeUseCase {
    Optional<CollegeResponse> update(UpdateCollegeCommand command);
}
