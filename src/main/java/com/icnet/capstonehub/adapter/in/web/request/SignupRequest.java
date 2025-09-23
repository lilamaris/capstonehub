package com.icnet.capstonehub.adapter.in.web.request;

public record SignupRequest (
    String name,
    String email,
    String password
) {}