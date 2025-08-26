package nuri.image_server.domain.file.presentation.dto;

import lombok.Builder;
import org.springframework.core.io.Resource;

@Builder
public record FileResourceWithType(
        String contentType,
        Resource resource
) {}
