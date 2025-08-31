package com.icnet.capstonehub.adapter.in.web.controller;

import com.icnet.capstonehub.adapter.in.web.dto.CreateCollegeRequest;
import com.icnet.capstonehub.adapter.in.web.dto.UpdateCollegeRequest;
import com.icnet.capstonehub.application.port.in.*;
import com.icnet.capstonehub.application.port.in.command.CreateCollegeCommand;
import com.icnet.capstonehub.application.port.in.command.UpdateCollegeCommand;
import com.icnet.capstonehub.application.port.in.response.CollegeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/college")
class CollegeController {
    private final CreateCollegeUseCase createCollegeUseCase;
    private final UpdateCollegeUseCase updateCollegeUseCase;
    private final FindAllCollegeUseCase findAllCollegeUseCase;
    private final FindCollegeUseCase findCollegeUseCase;
    private final DeleteCollegeUseCase deleteCollegeUseCase;

    @PostMapping
    public ResponseEntity<CollegeResponse> createCollege(@RequestBody CreateCollegeRequest createCollegeRequest) {
        CreateCollegeCommand collegeCommand = CreateCollegeCommand.builder()
                .name(createCollegeRequest.name())
                .effectiveStartDate(createCollegeRequest.effectiveStartDate())
                .effectiveEndDate(createCollegeRequest.effectiveEndDate())
                .build();
        CollegeResponse collegeResponse = createCollegeUseCase.create(collegeCommand);
        return ResponseEntity.ok(collegeResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CollegeResponse> updateCollege(@RequestBody UpdateCollegeRequest updateCollegeRequest, @PathVariable("id") Long id) {
        UpdateCollegeCommand collegeCommand = UpdateCollegeCommand.builder()
                .id(id)
                .name(updateCollegeRequest.name())
                .effectiveStartDate(updateCollegeRequest.effectiveStartDate())
                .effectiveEndDate(updateCollegeRequest.effectiveEndDate())
                .build();

        return updateCollegeUseCase.update(collegeCommand).map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCollege(@PathVariable("id") Long id) {
        deleteCollegeUseCase.byId(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity<List<CollegeResponse>> findCollege() {
        List<CollegeResponse> collegeResponses = findAllCollegeUseCase.find();
        return ResponseEntity.ok(collegeResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CollegeResponse> findCollegeById(@PathVariable("id") Long id) {
        return findCollegeUseCase.byId(id).map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
    }
}
