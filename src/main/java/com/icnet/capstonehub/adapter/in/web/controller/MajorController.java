package com.icnet.capstonehub.adapter.in.web.controller;

import com.icnet.capstonehub.adapter.in.web.dto.CreateMajorRequest;
import com.icnet.capstonehub.adapter.in.web.dto.UpdateMajorRequest;
import com.icnet.capstonehub.application.port.in.*;
import com.icnet.capstonehub.application.port.in.command.CreateMajorCommand;
import com.icnet.capstonehub.application.port.in.command.UpdateMajorCommand;
import com.icnet.capstonehub.application.port.in.response.MajorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/major")
class MajorController {
    private final CreateMajorUseCase createMajorUseCase;
    private final UpdateMajorUseCase updateMajorUseCase;
    private final FindAllMajorUseCase findAllMajorUseCase;
    private final FindMajorUseCase findMajorUseCase;
    private final DeleteMajorUseCase deleteMajorUseCase;

    @PostMapping
    public ResponseEntity<MajorResponse> createMajor(@RequestBody CreateMajorRequest createMajorRequest) {
        CreateMajorCommand majorCommand = CreateMajorCommand.builder()
                .name(createMajorRequest.name())
                .effectiveStartDate(createMajorRequest.effectiveStartDate())
                .effectiveEndDate(createMajorRequest.effectiveEndDate())
                .build();
        MajorResponse majorResponse = createMajorUseCase.create(majorCommand);
        return ResponseEntity.ok(majorResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MajorResponse> updateMajor(@RequestBody UpdateMajorRequest updateMajorRequest, @PathVariable("id") Long id) {
        UpdateMajorCommand majorCommand = UpdateMajorCommand.builder()
                .id(id)
                .name(updateMajorRequest.name())
                .effectiveStartDate(updateMajorRequest.effectiveStartDate())
                .effectiveEndDate(updateMajorRequest.effectiveEndDate())
                .build();
        try {
            MajorResponse majorResponse = updateMajorUseCase.update(majorCommand);
            return ResponseEntity.ok(majorResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMajor(@PathVariable("id") Long id) {
        deleteMajorUseCase.byId(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity<List<MajorResponse>> findMajor() {
        List<MajorResponse> majorResponses = findAllMajorUseCase.find();
        return ResponseEntity.ok(majorResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MajorResponse> findMajorById(@PathVariable("id") Long id) {
        try {
            MajorResponse majorResponse = findMajorUseCase.byId(id);
            return ResponseEntity.ok(majorResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
