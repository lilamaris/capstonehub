package com.icnet.capstonehub.application.port.in.command;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record AssignAffiliationCommand(
        @NotBlank Long majorId,
        @NotBlank Long collegeId,
        @NotBlank LocalDate effectiveStartDate,
        LocalDate effectiveEndDate
) {}
