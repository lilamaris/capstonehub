package com.icnet.capstonehub.application.port.in;

import com.icnet.capstonehub.application.port.in.command.CreateMajorCommand;
import com.icnet.capstonehub.application.port.in.command.UpdateMajorCommand;
import com.icnet.capstonehub.application.port.in.response.MajorResponse;

import java.util.List;

public interface MajorUseCase {
    MajorResponse createMajor(CreateMajorCommand command);
    MajorResponse updateMajor(UpdateMajorCommand command) throws Exception;
    MajorResponse findMajorById(Long id) throws Exception;
    List<MajorResponse> findMajor();
    void deleteMajor(Long id);
}
