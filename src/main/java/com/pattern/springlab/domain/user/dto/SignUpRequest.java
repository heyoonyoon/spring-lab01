package com.pattern.springlab.domain.user.dto;

public record SignUpRequest(String email, String password, String nickname) {}