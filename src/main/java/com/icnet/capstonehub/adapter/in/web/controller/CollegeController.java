package com.icnet.capstonehub.adapter.in.web.controller;

import com.icnet.capstonehub.adapter.in.web.dto.CreateCollegeRequest;
import com.icnet.capstonehub.application.port.in.CollegeUseCase;
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
    private final CollegeUseCase collegeUseCase;

    @PostMapping
    public ResponseEntity<CollegeResponse> createCollege(@RequestBody CreateCollegeRequest createCollegeRequest) {
        CreateCollegeCommand collegeCommand = CreateCollegeCommand.builder()
                .name(createCollegeRequest.name())
                .effectiveStartDate(createCollegeRequest.effectiveStartDate())
                .effectiveEndDate(createCollegeRequest.effectiveEndDate())
                .build();
        CollegeResponse collegeResponse = collegeUseCase.createCollege(collegeCommand);
        return ResponseEntity.ok(collegeResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CollegeResponse> updateCollege(@RequestBody UpdateCollegeCommand updateCollegeCommand, @PathVariable("id") Long id) {
        UpdateCollegeCommand collegeCommand = UpdateCollegeCommand.builder()
                .id(id)
                .name(updateCollegeCommand.name())
                .effectiveStartDate(updateCollegeCommand.effectiveStartDate())
                .effectiveEndDate(updateCollegeCommand.effectiveEndDate())
                .build();
        try {
            CollegeResponse collegeResponse = collegeUseCase.updateCollege(collegeCommand);
            return ResponseEntity.ok(collegeResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCollege(@PathVariable("id") Long id) {
        collegeUseCase.deleteCollege(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity<List<CollegeResponse>> findCollege() {
        List<CollegeResponse> collegeResponses = collegeUseCase.findCollege();
        return ResponseEntity.ok(collegeResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CollegeResponse> findCollegeById(@PathVariable("id") Long id) {
        try {
            CollegeResponse collegeResponse = collegeUseCase.findCollegeById(id);
            return ResponseEntity.ok(collegeResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
