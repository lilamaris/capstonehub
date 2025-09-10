package com.icnet.capstonehub.adapter.in.web.controller;

import com.icnet.capstonehub.adapter.in.web.mapper.AffiliationResponseMapper;
import com.icnet.capstonehub.adapter.in.web.request.AffiliationCreateLineageRequest;
import com.icnet.capstonehub.adapter.in.web.request.AffiliationLineageAmendRequest;
import com.icnet.capstonehub.adapter.in.web.response.AffiliationResponse;
import com.icnet.capstonehub.application.port.in.AffiliationUseCase;
import com.icnet.capstonehub.application.port.in.command.AffiliationLineageAmendCommand;
import com.icnet.capstonehub.application.port.in.command.AffiliationLineageAppendCommand;
import com.icnet.capstonehub.application.port.in.command.AffiliationLineageInitialCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/affiliation")
public class AffiliationController {
    private final AffiliationUseCase affiliationUseCase;

    @GetMapping("/lineage/{lineageId}")
    public ResponseEntity<List<AffiliationResponse>> getAffiliationLineage(
            @PathVariable("lineageId") UUID lineageId
    ) {
        List<AffiliationResponse> response = affiliationUseCase.getAffiliationLineage(lineageId).stream()
                .map(AffiliationResponseMapper::toResponse).toList();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/lineage")
    public ResponseEntity<AffiliationResponse> createAffiliationLineage(
            @RequestBody AffiliationCreateLineageRequest request
    ) {
        log.info("""
                [Controller] Initialize affiliation lineage local variables
                request={}""", request);

        var command = AffiliationLineageInitialCommand.builder()
                .collegeId(request.collegeId())
                .majorId(request.majorId())
                .validFrom(request.validFrom())
                .validTo(request.validTo())
                .versionDescription(request.versionDescription())
                .build();

        var response = AffiliationResponseMapper.toResponse(affiliationUseCase.initialAffiliationLineage(command));

        log.info("""
                [Controller] Initialize affiliation lineage local variables
                response={}""", response);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/lineage/{lineageSharedId}")
    public ResponseEntity<AffiliationResponse> assignAffiliationLineage(
            @PathVariable("lineageSharedId") UUID lineageSharedId,
            @RequestBody AffiliationCreateLineageRequest request
    ) {
        log.info("""
                [Controller] Assign affiliation lineage local variables
                pathVariable={}
                request={}""", lineageSharedId, request);

        var command = AffiliationLineageAppendCommand.builder()
                .lineageSharedId(lineageSharedId)
                .collegeId(request.collegeId())
                .majorId(request.majorId())
                .validFrom(request.validFrom())
                .validTo(request.validTo())
                .versionDescription(request.versionDescription())
                .build();

        AffiliationResponse response = AffiliationResponseMapper.toResponse(affiliationUseCase.appendAffiliationLineage(command));

        log.info("""
                [Controller] Assign affiliation lineage local variables
                response={}""", response);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/lineage/{lineageSharedId}/version/{versionSharedId}")
    public ResponseEntity<AffiliationResponse> amendAffiliationLineage(
            @PathVariable("lineageSharedId") UUID lineageSharedId,
            @PathVariable("versionSharedId") UUID versionSharedId,
            @RequestBody AffiliationLineageAmendRequest request
    ) {
        log.info("""
                [Controller] Amend affiliation lineage local variables
                pathVariable=(lineageSharedId={}, versionSharedId={})
                request={}""", lineageSharedId, versionSharedId, request);

        var command = AffiliationLineageAmendCommand.builder()
                .lineageSharedId(lineageSharedId)
                .versionSharedId(versionSharedId)
                .collegeId(request.collegeId())
                .majorId(request.majorId())
                .versionDescription(request.versionDescription())
                .build();

        AffiliationResponse response = AffiliationResponseMapper.toResponse(affiliationUseCase.amendAffiliationLineage(command));

        log.info("""
                [Controller] Amend affiliation lineage local variables
                response={}""", response);

        return ResponseEntity.ok(response);
    }
}
