package com.icnet.capstonehub.adapter.in.web.controller;

import com.icnet.capstonehub.adapter.in.web.request.CollegeCreateRequest;
import com.icnet.capstonehub.adapter.in.web.response.CollegeResponse;
import com.icnet.capstonehub.application.port.in.CollegeUseCase;
import com.icnet.capstonehub.application.port.in.command.CollegeCreateCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/college")
public class CollegeController {
    private final CollegeUseCase collegeUseCase;

    @GetMapping
    public ResponseEntity<List<CollegeResponse>> getCollege() {
        List<CollegeResponse> response = collegeUseCase.getAll().stream()
                .map(CollegeResponse::from).toList();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping
    public ResponseEntity<CollegeResponse> createCollege(@RequestBody CollegeCreateRequest request) {
        CollegeCreateCommand command = CollegeCreateCommand.builder()
                .name(request.name())
                .build();

        CollegeResponse response = CollegeResponse.from(collegeUseCase.createCollege(command));
        return ResponseEntity.ok(response);
    }
}
