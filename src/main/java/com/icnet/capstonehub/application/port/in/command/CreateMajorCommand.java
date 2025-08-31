package com.icnet.capstonehub.application.port.in.command;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CreateMajorCommand(
        @NotBlank String name,
        @NotBlank LocalDate effectiveStartDate,
        LocalDate effectiveEndDate
) {}
