package nuri.image_server.domain.file.presentation.dto.req;

import org.springframework.web.multipart.MultipartFile;

public record FileUploadRequestDto(
        String userId,
        MultipartFile file
) {
}
