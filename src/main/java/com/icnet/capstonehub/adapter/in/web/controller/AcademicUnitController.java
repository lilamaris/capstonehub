package com.icnet.capstonehub.adapter.in.web.controller;

import com.icnet.capstonehub.adapter.in.security.model.SecurityUser;
import com.icnet.capstonehub.adapter.in.web.request.AcademicUnitCreateTimelineRequest;
import com.icnet.capstonehub.adapter.in.web.request.AcademicUnitAmendTimelineRequest;
import com.icnet.capstonehub.adapter.in.web.response.AcademicUnitResponse;
import com.icnet.capstonehub.application.port.in.AcademicUnitUseCase;
import com.icnet.capstonehub.application.port.in.command.AcademicUnitAmendTimelineCommand;
import com.icnet.capstonehub.application.port.in.command.AcademicUnitAppendTimelineCommand;
import com.icnet.capstonehub.application.port.in.command.AcademicUnitInitialTimelineCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/academicUnit")
public class AcademicUnitController {
    private final AcademicUnitUseCase academicUnitUseCase;

    @GetMapping()
    public ResponseEntity<List<AcademicUnitResponse>> getAllByUserId(
            Authentication authentication
    ) {
        SecurityUser user = (SecurityUser) authentication.getPrincipal();
        List<AcademicUnitResponse> response = academicUnitUseCase.getAllAcademicUnitByUser(user.getUser().id()).stream()
                .map(AcademicUnitResponse::from).toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/timeline/{timelineId}")
    public ResponseEntity<List<AcademicUnitResponse>> getAcademicUnitTimeline(
            @PathVariable("timelineId") UUID timelineId
    ) {
        List<AcademicUnitResponse> response = academicUnitUseCase.getAcademicUnitTimeline(timelineId).stream()
                .map(AcademicUnitResponse::from).toList();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/timeline")
    public ResponseEntity<AcademicUnitResponse> createAcademicUnitTimeline(
            @RequestBody AcademicUnitCreateTimelineRequest request
    ) {
        log.info("""
                [Controller] Initialize academicUnit timeline local variables
                request={}""", request);

        var command = AcademicUnitInitialTimelineCommand.builder()
                .facultyId(request.facultyId())
                .departmentId(request.departmentId())
                .validAt(request.validAt())
                .build();

        var response = AcademicUnitResponse.from(academicUnitUseCase.initialAcademicUnitTimeline(command));

        log.info("""
                [Controller] Initialize academicUnit timeline local variables
                response={}""", response);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/timeline/{timelineSharedId}")
    public ResponseEntity<AcademicUnitResponse> appendAcademicUnitTimeline(
            @PathVariable("timelineSharedId") UUID timelineSharedId,
            @RequestBody AcademicUnitCreateTimelineRequest request
    ) {
        log.info("""
                [Controller] Assign academicUnit timeline local variables
                pathVariable={}
                request={}""", timelineSharedId, request);

        var command = AcademicUnitAppendTimelineCommand.builder()
                .timelineSharedId(timelineSharedId)
                .facultyId(request.facultyId())
                .departmentId(request.departmentId())
                .validAt(request.validAt())
                .build();

        AcademicUnitResponse response = AcademicUnitResponse.from(academicUnitUseCase.appendAcademicUnitTimeline(command));

        log.info("""
                [Controller] Assign academicUnit timeline local variables
                response={}""", response);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/timeline/{timelineSharedId}/edition/{editionSharedId}")
    public ResponseEntity<AcademicUnitResponse> amendAcademicUnitTimeline(
            @PathVariable("timelineSharedId") UUID timelineSharedId,
            @PathVariable("editionSharedId") UUID editionSharedId,
            @RequestBody AcademicUnitAmendTimelineRequest request
    ) {
        log.info("""
                [Controller] Amend academicUnit timeline local variables
                pathVariable=(timelineSharedId={}, editionSharedId={})
                request={}""", timelineSharedId, editionSharedId, request);

        var command = AcademicUnitAmendTimelineCommand.builder()
                .timelineSharedId(timelineSharedId)
                .editionSharedId(editionSharedId)
                .facultyId(request.facultyId())
                .departmentId(request.departmentId())
                .editionDescription(request.editionDescription())
                .build();

        AcademicUnitResponse response = AcademicUnitResponse.from(academicUnitUseCase.amendAcademicUnitTimeline(command));

        log.info("""
                [Controller] Amend academicUnit timeline local variables
                response={}""", response);

        return ResponseEntity.ok(response);
    }
}
