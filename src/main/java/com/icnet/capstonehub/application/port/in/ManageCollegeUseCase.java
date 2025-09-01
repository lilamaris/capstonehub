package com.icnet.capstonehub.application.port.in;

import com.icnet.capstonehub.application.port.in.command.CreateCollegeCommand;
import com.icnet.capstonehub.application.port.in.command.UpdateCollegeCommand;
import com.icnet.capstonehub.application.port.in.response.CollegeResponse;

import java.util.List;
import java.util.Optional;

public interface ManageCollegeUseCase {
    Optional<CollegeResponse> findById(Long id);
    List<CollegeResponse> findAll();
    CollegeResponse create(CreateCollegeCommand command);
    Optional<CollegeResponse> update(UpdateCollegeCommand command);
    void deleteById(Long id);
}
