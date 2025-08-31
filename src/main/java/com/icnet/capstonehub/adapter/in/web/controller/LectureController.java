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
    private final CreateLectureUseCase createLectureUseCase;
    private final UpdateLectureUseCase updateLectureUseCase;
    private final FindAllLectureUseCase findAllLectureUseCase;
    private final FindLectureUseCase findLectureUseCase;
    private final DeleteLectureUseCase deleteLectureUseCase;

    @PostMapping
    public ResponseEntity<LectureResponse> createLecture(@RequestBody CreateLectureRequest createLectureRequest) {
        CreateLectureCommand createLectureCommand = CreateLectureCommand.builder()
                .name(createLectureRequest.name())
                .effectiveStartDate(createLectureRequest.effectiveStartDate())
                .effectiveEndDate(createLectureRequest.effectiveEndDate())
                .build();
        LectureResponse lectureResponse = createLectureUseCase.create(createLectureCommand);
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
        try {
            LectureResponse lectureResponse = updateLectureUseCase.update(lectureCommand);
            return ResponseEntity.ok(lectureResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLecture(@PathVariable("id") Long id) {
        deleteLectureUseCase.byId(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity<List<LectureResponse>> findLecture() {
        List<LectureResponse> lectureResponses = findAllLectureUseCase.find();
        return ResponseEntity.ok(lectureResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LectureResponse> findLectureById(@PathVariable("id") Long id) {
        try {
            LectureResponse lectureResponse = findLectureUseCase.byId(id);
            return ResponseEntity.ok(lectureResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
