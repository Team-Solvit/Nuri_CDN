package nuri.image_server.domain.file.presentation.controller;

import lombok.RequiredArgsConstructor;
import nuri.image_server.domain.file.application.service.FileService;
import nuri.image_server.domain.file.domain.exception.FileEmptyException;
import nuri.image_server.domain.file.domain.exception.FileInvalidException;
import nuri.image_server.domain.file.domain.exception.FileNotFoundException;
import nuri.image_server.domain.file.presentation.dto.FileDeleteRequest;
import nuri.image_server.domain.file.presentation.dto.FileResourceWithType;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class FileController {
    private final FileService fileService;

    @GetMapping("/{fileId}")
    public ResponseEntity<Resource> readFile(@PathVariable UUID fileId) {
        if(fileId == null) throw new FileNotFoundException();
        FileResourceWithType fileResourceWithType = fileService.readFile(fileId);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(fileResourceWithType.contentType())).body(fileResourceWithType.resource());
    }

    @PostMapping("/")
    public ResponseEntity<List<UUID>> uploadFiles(@RequestParam("files") List<MultipartFile> files, @RequestParam String secretKey) {
        for(MultipartFile multipartFile : files) {
            if(multipartFile.isEmpty()) {
                throw new FileInvalidException();
            }
        }
        return ResponseEntity.ok(fileService.uploadFiles(secretKey, files));
    }

    @DeleteMapping("/")
    public void deleteFiles(@RequestBody FileDeleteRequest fileDeleteRequest) {
        for(UUID fileId : fileDeleteRequest.fileIds()) {
            if(fileId == null) {
                throw new FileEmptyException();
            }
        }

        fileService.deleteFiles(fileDeleteRequest.secretKey(), fileDeleteRequest.fileIds());
    }
}
