package com.icnet.capstonehub.adapter.in.web.controller;

import com.icnet.capstonehub.adapter.in.web.request.MajorCreateRequest;
import com.icnet.capstonehub.adapter.in.web.response.MajorResponse;
import com.icnet.capstonehub.application.port.in.MajorUseCase;
import com.icnet.capstonehub.application.port.in.command.MajorCreateCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/major")
public class MajorController {
    private final MajorUseCase majorUseCase;

    @GetMapping
    public ResponseEntity<List<MajorResponse>> getAll() {
        List<MajorResponse> response = majorUseCase.getAll().stream().map(MajorResponse::from).toList();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping
    public ResponseEntity<MajorResponse> createMajor(@RequestBody MajorCreateRequest request) {
        MajorCreateCommand command = MajorCreateCommand.builder()
                .name(request.name())
                .build();

        MajorResponse response = MajorResponse.from(majorUseCase.createMajor(command));
        return ResponseEntity.ok(response);
    }
}
