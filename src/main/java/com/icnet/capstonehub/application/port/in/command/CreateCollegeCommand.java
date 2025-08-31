package com.icnet.capstonehub.application.port.in.command;

import lombok.Builder;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

@Builder
public record CreateCollegeCommand(
        @NotBlank String name,
        @NotBlank LocalDate effectiveStartDate,
        LocalDate effectiveEndDate
) {}
