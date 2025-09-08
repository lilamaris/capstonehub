package com.icnet.capstonehub.adapter.in.web.controller;

import com.icnet.capstonehub.adapter.in.web.mapper.AffiliationResponseMapper;
import com.icnet.capstonehub.adapter.in.web.request.AffiliationCreateLineageRequest;
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

    @PostMapping
    public ResponseEntity<AffiliationResponse> createAffiliationLineage(
            @RequestBody AffiliationCreateLineageRequest request
    ) {
        log.info("Create affiliation lineage command with request={}", request.toString());
        AffiliationResponse response = AffiliationResponseMapper.toResponse(
                affiliationUseCase.createAffiliationLineage(
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
