package com.icnet.capstonehub.adapter.in.web.controller;

import com.icnet.capstonehub.adapter.in.web.dto.CreateCollegeRequest;
import com.icnet.capstonehub.application.port.in.CreateCollegeUseCase;
import com.icnet.capstonehub.application.port.in.command.CreateCollegeCommand;
import com.icnet.capstonehub.application.port.in.response.CreateCollegeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/college")
public class CollegeController {
    private final CreateCollegeUseCase createCollegeUseCase;

    @PostMapping
    public ResponseEntity<CreateCollegeResponse> createCollege(@RequestBody CreateCollegeRequest createCollegeRequest) {
        CreateCollegeCommand collegeCommand = CreateCollegeCommand.builder()
                .name(createCollegeRequest.getName())
                .effectiveStartDate(createCollegeRequest.getEffectiveStartDate())
                .effectiveEndDate(createCollegeRequest.getEffectiveEndDate())
                .build();
        CreateCollegeResponse collegeResponse = createCollegeUseCase.createCollege(collegeCommand);
        return ResponseEntity.ok(collegeResponse);
    }
}
