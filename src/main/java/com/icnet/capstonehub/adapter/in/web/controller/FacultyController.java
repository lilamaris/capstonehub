package com.icnet.capstonehub.adapter.in.web.controller;

import com.icnet.capstonehub.adapter.in.web.request.FacultyCreateRequest;
import com.icnet.capstonehub.adapter.in.web.response.FacultyResponse;
import com.icnet.capstonehub.application.port.in.FacultyUseCase;
import com.icnet.capstonehub.application.port.in.command.FacultyCreateCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/faculty")
public class FacultyController {
    private final FacultyUseCase facultyUseCase;

    @GetMapping
    public ResponseEntity<List<FacultyResponse>> getFaculty() {
        List<FacultyResponse> response = facultyUseCase.getAll().stream()
                .map(FacultyResponse::from).toList();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping
    public ResponseEntity<FacultyResponse> createFaculty(@RequestBody FacultyCreateRequest request) {
        FacultyCreateCommand command = FacultyCreateCommand.builder()
                .name(request.name())
                .build();

        FacultyResponse response = FacultyResponse.from(facultyUseCase.createFaculty(command));
        return ResponseEntity.ok(response);
    }
}
