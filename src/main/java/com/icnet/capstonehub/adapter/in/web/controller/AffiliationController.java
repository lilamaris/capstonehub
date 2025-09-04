package com.icnet.capstonehub.adapter.in.web.controller;

import com.icnet.capstonehub.adapter.in.web.dto.AssignAffiliationRequest;
import com.icnet.capstonehub.adapter.in.web.dto.ReassignAffiliationRequest;
import com.icnet.capstonehub.application.port.in.ManageAffiliationUseCase;
import com.icnet.capstonehub.application.port.in.command.AssignAffiliationCommand;
import com.icnet.capstonehub.application.port.in.command.ReassignAffiliationCommand;
import com.icnet.capstonehub.application.port.out.response.AffiliationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/affiliation")
public class AffiliationController {
    private final ManageAffiliationUseCase manageAffiliationUseCase;

    @PostMapping
    public ResponseEntity<AffiliationResponse> assignAffiliation(@RequestBody AssignAffiliationRequest request) {
        AssignAffiliationCommand command = AssignAffiliationCommand.builder()
                .collegeId(request.collegeId())
                .majorId(request.majorId())
                .effectiveStartDate(request.effectiveStartDate())
                .effectiveEndDate(request.effectiveEndDate())
                .build();

        AffiliationResponse response = manageAffiliationUseCase.assignMajorToCollege(command);
        return ResponseEntity.ok(response);
    }
}
