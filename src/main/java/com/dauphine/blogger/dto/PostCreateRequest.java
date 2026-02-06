package com.dauphine.blogger.dto;

import java.util.UUID;

public record PostCreateRequest(
        String title,
        String content,
        UUID categoryId
) {}

