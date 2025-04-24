package com.bexos.authorization_server.dto;

import lombok.Builder;

@Builder
public record ExceptionResponse(
        String message
) {
}
