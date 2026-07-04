package com.example.blog_backend.modules.user.dto;

import java.util.Date;
import java.util.UUID;

import com.example.blog_backend.modules.user.enums.UserEnum;

public record UserResponseDTO(
    UUID id,
    String name,
    String email,
    UserEnum role,
    Date createdAt,
    Date updatedAt
) {
    
}
