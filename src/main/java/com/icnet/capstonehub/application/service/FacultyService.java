package com.icnet.capstonehub.application.service;

import com.icnet.capstonehub.application.port.in.FacultyUseCase;
import com.icnet.capstonehub.application.port.in.command.FacultyCreateCommand;
import com.icnet.capstonehub.application.port.in.result.FacultyResult;
import com.icnet.capstonehub.application.port.out.FacultyPort;
import com.icnet.capstonehub.domain.model.Faculty;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FacultyService implements FacultyUseCase {
    private final FacultyPort facultyPort;

    @Override
    public List<FacultyResult> getAll() {
        return facultyPort.getAll().stream().map(FacultyResult::from).toList();
    }

    @Override
    public FacultyResult createFaculty(FacultyCreateCommand command) {
        Faculty domain = Faculty.builder().name(command.name()).build();
        return FacultyResult.from(facultyPort.save(domain));
    }
}
