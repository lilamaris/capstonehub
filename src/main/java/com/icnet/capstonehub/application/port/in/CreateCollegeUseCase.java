package com.icnet.capstonehub.application.port.in;

import com.icnet.capstonehub.application.port.in.command.CreateCollegeCommand;
import com.icnet.capstonehub.application.port.in.response.CreateCollegeResponse;

public interface CreateCollegeUseCase {
    CreateCollegeResponse createCollege(CreateCollegeCommand command);
}
