package nuri.image_server.global.exception;

import lombok.Builder;

import java.time.OffsetDateTime;

@Builder
public record ErrorResponse(
        int status,
        String message,
        OffsetDateTime timestamp
) {}
