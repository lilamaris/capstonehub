package com.icnet.capstonehub.adapter.in.web.controller;

import com.icnet.capstonehub.adapter.in.web.request.DepartmentCreateRequest;
import com.icnet.capstonehub.adapter.in.web.response.DepartmentResponse;
import com.icnet.capstonehub.application.port.in.DepartmentUseCase;
import com.icnet.capstonehub.application.port.in.command.DepartmentCreateCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/department")
public class DepartmentController {
    private final DepartmentUseCase departmentUseCase;

    @GetMapping
    public ResponseEntity<List<DepartmentResponse>> getAll() {
        List<DepartmentResponse> response = departmentUseCase.getAll().stream().map(DepartmentResponse::from).toList();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping
    public ResponseEntity<DepartmentResponse> createDepartment(@RequestBody DepartmentCreateRequest request) {
        DepartmentCreateCommand command = DepartmentCreateCommand.builder()
                .name(request.name())
                .build();

        DepartmentResponse response = DepartmentResponse.from(departmentUseCase.createDepartment(command));
        return ResponseEntity.ok(response);
    }
}
