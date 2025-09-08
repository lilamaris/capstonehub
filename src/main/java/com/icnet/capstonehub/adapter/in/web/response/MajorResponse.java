package com.icnet.capstonehub.adapter.in.web.response;

import lombok.Builder;

import java.util.UUID;

@Builder
public record MajorResponse(UUID id, String name) {}
