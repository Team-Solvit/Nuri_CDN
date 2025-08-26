package nuri.image_server.domain.file.presentation.dto;

import java.util.List;
import java.util.UUID;

public record FileDeleteRequest (
        List<UUID> fileIds,
        String secretKey
) {}