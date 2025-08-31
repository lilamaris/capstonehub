package com.icnet.capstonehub.adapter.in.web.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record UpdateLectureRequest(
        @NotBlank String name,
        @NotBlank LocalDate effectiveStartDate,
        @Nullable LocalDate effectiveEndDate
) {}
