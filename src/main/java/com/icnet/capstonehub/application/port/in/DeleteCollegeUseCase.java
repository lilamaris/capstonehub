package com.icnet.capstonehub.application.port.in;

import com.icnet.capstonehub.application.port.in.command.CreateCollegeCommand;
import com.icnet.capstonehub.application.port.in.command.UpdateCollegeCommand;
import com.icnet.capstonehub.application.port.in.response.CollegeResponse;

import java.util.List;

public interface DeleteCollegeUseCase {
    void byId(Long id);
}
