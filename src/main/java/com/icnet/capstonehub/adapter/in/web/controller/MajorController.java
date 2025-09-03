package com.icnet.capstonehub.adapter.in.web.controller;

import com.icnet.capstonehub.adapter.in.web.dto.CreateMajorRequest;
import com.icnet.capstonehub.adapter.in.web.dto.UpdateMajorRequest;
import com.icnet.capstonehub.application.port.in.*;
import com.icnet.capstonehub.application.port.in.command.CreateMajorCommand;
import com.icnet.capstonehub.application.port.in.command.UpdateMajorCommand;
import com.icnet.capstonehub.application.port.out.response.MajorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/major")
class MajorController {
    private final ManageMajorUseCase manageMajorUseCase;

    @PostMapping
    public ResponseEntity<MajorResponse> createMajor(@RequestBody CreateMajorRequest createMajorRequest) {
        CreateMajorCommand command = CreateMajorCommand.builder()
                .name(createMajorRequest.name())
                .effectiveStartDate(createMajorRequest.effectiveStartDate())
                .effectiveEndDate(createMajorRequest.effectiveEndDate())
                .build();
        MajorResponse response = manageMajorUseCase.create(command);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MajorResponse> updateMajor(@RequestBody UpdateMajorRequest updateMajorRequest, @PathVariable("id") Long id) {
        UpdateMajorCommand command = UpdateMajorCommand.builder()
                .id(id)
                .name(updateMajorRequest.name())
                .effectiveStartDate(updateMajorRequest.effectiveStartDate())
                .effectiveEndDate(updateMajorRequest.effectiveEndDate())
                .build();

        MajorResponse response = manageMajorUseCase.update(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMajor(@PathVariable("id") Long id) {
        manageMajorUseCase.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity<List<MajorResponse>> findMajor() {
        List<MajorResponse> responses = manageMajorUseCase.findAll();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MajorResponse> findMajorById(@PathVariable("id") Long id) {
        return manageMajorUseCase.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
    }
}
