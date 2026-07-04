package com.example.blog_backend.modules.login.dto;

public record LoginResponseDTO(
        String token,
        String userId
) {}
