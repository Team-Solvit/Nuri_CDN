package nuri.image_server.domain.file.presentation.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record FileDeleteRequest (
        @NotNull
        List<@NotNull UUID> fileIds,
        @NotNull
        String secretKey
) {}