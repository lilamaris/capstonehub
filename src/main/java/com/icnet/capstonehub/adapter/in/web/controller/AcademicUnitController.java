package com.icnet.capstonehub.adapter.in.web.controller;

import com.icnet.capstonehub.adapter.in.security.model.SecurityUser;
import com.icnet.capstonehub.adapter.in.web.request.AcademicUnitCreateTimelineRequest;
import com.icnet.capstonehub.adapter.in.web.request.AcademicUnitAmendTimelineRequest;
import com.icnet.capstonehub.adapter.in.web.response.AcademicUnitResponse;
import com.icnet.capstonehub.application.port.in.AcademicUnitCommandUseCase;
import com.icnet.capstonehub.application.port.in.AcademicUnitQueryUseCase;
import com.icnet.capstonehub.application.port.in.command.AcademicUnitAmendTimelineCommand;
import com.icnet.capstonehub.application.port.in.command.AcademicUnitAppendTimelineCommand;
import com.icnet.capstonehub.application.port.in.command.AcademicUnitInitialTimelineCommand;
import com.icnet.capstonehub.domain.model.Timeline;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/academicUnit")
public class AcademicUnitController {
    private final AcademicUnitCommandUseCase commandUseCase;
    private final AcademicUnitQueryUseCase queryUseCase;

    @GetMapping()
    public ResponseEntity<List<AcademicUnitResponse.Query>> getAllByUserId(
            Authentication authentication
    ) {
        SecurityUser user = (SecurityUser) authentication.getPrincipal();
        var response = queryUseCase.getOwned(user.getUser().id()).stream()
                .map(AcademicUnitResponse.Query::from).toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/timeline/shared/{timelineSharedId}")
    public ResponseEntity<List<AcademicUnitResponse.Query>> getAcademicUnitTimeline(
            @PathVariable("timelineSharedId") UUID timelineSharedId
    ) {
        var id = Timeline.SharedId.from(timelineSharedId);
        var response = queryUseCase.getTimeline(id).stream()
                .map(AcademicUnitResponse.Query::from).toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/timeline/shared/{timelineSharedId}/snapshot/{txAt}")
    public ResponseEntity<List<AcademicUnitResponse.Query>> getSnapshot(
            @PathVariable("timelineSharedId") UUID timelineSharedId,
            @PathVariable("txAt") LocalDateTime txAt
    ) {
        var id = Timeline.SharedId.from(timelineSharedId);
        var response = queryUseCase.getSnapshot(id, txAt).stream()
                .map(AcademicUnitResponse.Query::from).toList();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/timeline")
    public ResponseEntity<AcademicUnitResponse.Command> createAcademicUnitTimeline(
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

        var response = AcademicUnitResponse.Command.from(commandUseCase.initialTimeline(command));

        log.info("""
                [Controller] Initialize academicUnit timeline local variables
                response={}""", response);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/timeline/shared/{timelineSharedId}")
    public ResponseEntity<AcademicUnitResponse.Command> appendAcademicUnitTimeline(
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

        var response = AcademicUnitResponse.Command.from(commandUseCase.appendTimeline(command));

        log.info("""
                [Controller] Assign academicUnit timeline local variables
                response={}""", response);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/timeline/shared/{timelineSharedId}/edition/shared/{editionSharedId}")
    public ResponseEntity<AcademicUnitResponse.Command> amendAcademicUnitTimeline(
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

        var response = AcademicUnitResponse.Command.from(commandUseCase.amendTimeline(command));

        log.info("""
                [Controller] Amend academicUnit timeline local variables
                response={}""", response);

        return ResponseEntity.ok(response);
    }
}
