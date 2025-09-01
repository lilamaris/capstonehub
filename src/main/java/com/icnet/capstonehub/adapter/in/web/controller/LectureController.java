package com.icnet.capstonehub.adapter.in.web.controller;

import com.icnet.capstonehub.adapter.in.web.dto.CreateLectureRequest;
import com.icnet.capstonehub.adapter.in.web.dto.UpdateLectureRequest;
import com.icnet.capstonehub.application.port.in.*;
import com.icnet.capstonehub.application.port.in.command.CreateLectureCommand;
import com.icnet.capstonehub.application.port.in.command.UpdateLectureCommand;
import com.icnet.capstonehub.application.port.in.response.LectureResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lecture")
class LectureController {
    private final ManageLectureUseCase manageLectureUseCase;

    @GetMapping()
    public ResponseEntity<List<LectureResponse>> findLecture() {
        List<LectureResponse> lectureResponses = manageLectureUseCase.findAll();
        return ResponseEntity.ok(lectureResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LectureResponse> findLectureById(@PathVariable("id") Long id) {
        return manageLectureUseCase.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
    }

    @PostMapping
    public ResponseEntity<LectureResponse> createLecture(@RequestBody CreateLectureRequest createLectureRequest) {
        CreateLectureCommand createLectureCommand = CreateLectureCommand.builder()
                .name(createLectureRequest.name())
                .effectiveStartDate(createLectureRequest.effectiveStartDate())
                .effectiveEndDate(createLectureRequest.effectiveEndDate())
                .build();
        LectureResponse lectureResponse = manageLectureUseCase.create(createLectureCommand);
        return ResponseEntity.ok(lectureResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LectureResponse> updateLecture(@RequestBody UpdateLectureRequest updateLectureRequest, @PathVariable("id") Long id) {
        UpdateLectureCommand lectureCommand = UpdateLectureCommand.builder()
                .id(id)
                .name(updateLectureRequest.name())
                .effectiveStartDate(updateLectureRequest.effectiveStartDate())
                .effectiveEndDate(updateLectureRequest.effectiveEndDate())
                .build();

        return manageLectureUseCase.update(lectureCommand).map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLecture(@PathVariable("id") Long id) {
        manageLectureUseCase.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
