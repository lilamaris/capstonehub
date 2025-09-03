package com.icnet.capstonehub.adapter.in.web.controller;

import com.icnet.capstonehub.adapter.in.web.dto.CreateCollegeRequest;
import com.icnet.capstonehub.adapter.in.web.dto.UpdateCollegeRequest;
import com.icnet.capstonehub.application.port.in.*;
import com.icnet.capstonehub.application.port.in.command.CreateCollegeCommand;
import com.icnet.capstonehub.application.port.in.command.UpdateCollegeCommand;
import com.icnet.capstonehub.application.port.out.response.CollegeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/college")
class CollegeController {
    private final ManageCollegeUseCase manageCollegeUseCase;

    @PostMapping
    public ResponseEntity<CollegeResponse> createCollege(@RequestBody CreateCollegeRequest request) {
        CreateCollegeCommand command = CreateCollegeCommand.builder()
                .name(request.name())
                .effectiveStartDate(request.effectiveStartDate())
                .effectiveEndDate(request.effectiveEndDate())
                .build();

        CollegeResponse response = manageCollegeUseCase.create(command);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CollegeResponse> updateCollege(@RequestBody UpdateCollegeRequest request, @PathVariable("id") Long id) {
        UpdateCollegeCommand command = UpdateCollegeCommand.builder()
                .id(id)
                .name(request.name())
                .effectiveStartDate(request.effectiveStartDate())
                .effectiveEndDate(request.effectiveEndDate())
                .build();

        CollegeResponse response = manageCollegeUseCase.update(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCollege(@PathVariable("id") Long id) {
        manageCollegeUseCase.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity<List<CollegeResponse>> findCollege() {
        List<CollegeResponse> response = manageCollegeUseCase.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CollegeResponse> findCollegeById(@PathVariable("id") Long id) {
        return manageCollegeUseCase.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
    }
}
