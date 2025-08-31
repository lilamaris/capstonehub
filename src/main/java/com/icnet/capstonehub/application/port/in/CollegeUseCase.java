package com.icnet.capstonehub.application.port.in;

import com.icnet.capstonehub.application.port.in.command.CreateCollegeCommand;
import com.icnet.capstonehub.application.port.in.command.UpdateCollegeCommand;
import com.icnet.capstonehub.application.port.in.response.CollegeResponse;

import java.util.List;

public interface CollegeUseCase {
    CollegeResponse createCollege(CreateCollegeCommand command);
    CollegeResponse updateCollege(UpdateCollegeCommand command) throws Exception;
    CollegeResponse findCollegeById(Long id) throws Exception;
    List<CollegeResponse> findCollege();
}
