package com.dauphine.blogger.dto;

import java.util.UUID;

public record PostResponse(
        UUID id,
        String title,
        String content,
        java.time.LocalDateTime createdDate,
        CategoryResponse category
) {}
