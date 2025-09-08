package com.icnet.capstonehub.application.port.in;

import com.icnet.capstonehub.application.port.in.command.CollegeCreateCommand;
import com.icnet.capstonehub.application.port.in.result.CollegeResult;

import java.util.List;

public interface CollegeUseCase {
    List<CollegeResult> getAll();
    CollegeResult createCollege(CollegeCreateCommand command);
}
