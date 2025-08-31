package com.icnet.capstonehub.application.port.in;

import com.icnet.capstonehub.application.port.in.command.UpdateCollegeCommand;
import com.icnet.capstonehub.application.port.in.response.CollegeResponse;

public interface UpdateCollegeUseCase {
    CollegeResponse update(UpdateCollegeCommand command);
}
