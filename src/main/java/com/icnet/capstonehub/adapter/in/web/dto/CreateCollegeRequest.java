package com.icnet.capstonehub.adapter.in.web.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.LocalDate;

@Value
@EqualsAndHashCode(callSuper = false)
public class CreateCollegeRequest {
    @NotBlank
    String name;

    @NotBlank
    LocalDate effectiveStartDate;

    @Nullable
    LocalDate effectiveEndDate;
}
