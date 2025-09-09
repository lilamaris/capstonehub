package com.icnet.capstonehub.adapter.in.web.controller;

import com.icnet.capstonehub.adapter.in.web.mapper.AffiliationResponseMapper;
import com.icnet.capstonehub.adapter.in.web.request.AffiliationCreateLineageRequest;
import com.icnet.capstonehub.adapter.in.web.request.AffiliationLineageAmendRequest;
import com.icnet.capstonehub.adapter.in.web.response.AffiliationResponse;
import com.icnet.capstonehub.application.port.in.AffiliationUseCase;
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

    @GetMapping("/{lineageId}")
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
        log.info("Create affiliation lineage command with request={}", request.toString());
        AffiliationResponse response = AffiliationResponseMapper.toResponse(
                affiliationUseCase.initialAffiliationLineage(
                        request.collegeId(),
                        request.majorId(),
                        request.validFrom(),
                        request.validTo(),
                        request.versionDescription()
                )
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/lineage/{lineageId}")
    public ResponseEntity<AffiliationResponse> assignAffiliationLineage(
            @PathVariable("lineageId") UUID lineageId,
            @RequestBody AffiliationCreateLineageRequest request
    ) {
        log.info("Assign affiliation lineage command with request={}", request.toString());
        AffiliationResponse response = AffiliationResponseMapper.toResponse(
                affiliationUseCase.appendAffiliationLineage(
                        lineageId,
                        request.collegeId(),
                        request.majorId(),
                        request.validFrom(),
                        request.validTo(),
                        request.versionDescription()
                )
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/lineage/{lineageId}")
    public ResponseEntity<AffiliationResponse> amendAffiliationLineage(
            @PathVariable("lineageId") UUID lineageId,
            @RequestBody AffiliationLineageAmendRequest request
    ) {
        log.info("Assign affiliation lineage command with request={}", request.toString());

        AffiliationResponse response = AffiliationResponseMapper.toResponse(
                affiliationUseCase.amendAffiliationLineage(
                        lineageId,
                        request.id(),
                        request.collegeId(),
                        request.majorId(),
                        request.validFrom(),
                        request.validTo(),
                        request.versionDescription()
                )
        );

        return ResponseEntity.ok(response);
    }
}
