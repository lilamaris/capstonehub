package com.icnet.capstonehub.application.port.in;

import com.icnet.capstonehub.application.port.in.command.CreateCollegeCommand;
import com.icnet.capstonehub.application.port.in.response.CollegeResponse;

public interface CreateCollegeUseCase {
    CollegeResponse create(CreateCollegeCommand command);
}
