package com.dauphine.blogger.dto;

import java.util.UUID;

public record PostUpdateRequest(
        String title,
        String content,
        UUID categoryId
) {}
