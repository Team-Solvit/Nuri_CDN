package nuri.image_server.domain.file.presentation.controller;

import lombok.RequiredArgsConstructor;
import nuri.image_server.domain.file.application.service.FileService;
import nuri.image_server.domain.file.domain.exception.FileEmptyException;
import nuri.image_server.domain.file.domain.exception.FileInvalidException;
import nuri.image_server.domain.file.domain.exception.FileNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @GetMapping("/{fileId}")
    public ResponseEntity<Resource> readFile(@PathVariable UUID fileId) {
        if(fileId == null) throw new FileNotFoundException();
        return ResponseEntity.ok(fileService.readFile(fileId));
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
    public void deleteFiles(@RequestParam("fileId") List<UUID> fileIds, @RequestParam String secretKey) {
        for(UUID fileId : fileIds) {
            if(fileId == null) {
                throw new FileEmptyException();
            }
        }

        fileService.deleteFiles(secretKey, fileIds);
    }
}
