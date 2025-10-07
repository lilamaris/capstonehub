package com.icnet.capstonehub.application.port.in;

import com.icnet.capstonehub.application.port.in.command.FacultyCreateCommand;
import com.icnet.capstonehub.application.port.in.result.FacultyResult;

import java.util.List;

public interface FacultyUseCase {
    List<FacultyResult> getAll();
    FacultyResult createFaculty(FacultyCreateCommand command);
}
