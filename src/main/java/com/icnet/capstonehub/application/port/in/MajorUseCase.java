package com.icnet.capstonehub.application.port.in;

import com.icnet.capstonehub.application.port.in.command.MajorCreateCommand;
import com.icnet.capstonehub.application.port.in.result.MajorResult;

import java.util.List;

public interface MajorUseCase {
    List<MajorResult> getAll();
    MajorResult createMajor(MajorCreateCommand command);
}

