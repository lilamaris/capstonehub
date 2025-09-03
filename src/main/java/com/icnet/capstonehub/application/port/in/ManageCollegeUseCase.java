package com.icnet.capstonehub.application.port.in;

import com.icnet.capstonehub.application.port.in.command.CreateCollegeCommand;
import com.icnet.capstonehub.application.port.in.command.UpdateCollegeCommand;
import com.icnet.capstonehub.application.port.out.response.CollegeResponse;

import java.util.List;
import java.util.Optional;

public interface ManageCollegeUseCase {
    CollegeResponse create(CreateCollegeCommand command);
    CollegeResponse update(UpdateCollegeCommand command);
    void deleteById(Long id);
    Optional<CollegeResponse> findById(Long id);
    List<CollegeResponse> findAll();
}
