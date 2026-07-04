package com.example.blog_backend.modules.post.dto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public record PostResponseDTO(
        UUID id,
        String title,
        String shortDescription,
        String content,
        UUID author,
        List<String> tags,
        String category,
        String slug,
        String status,
        int views,
        Date createdAt,
        Date updatedAt
) {
}
