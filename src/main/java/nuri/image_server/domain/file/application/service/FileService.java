package nuri.image_server.domain.file.application.service;

import lombok.RequiredArgsConstructor;
import nuri.image_server.domain.file.domain.entity.FileEntity;
import nuri.image_server.domain.file.domain.exception.FileEmptyException;
import nuri.image_server.domain.file.domain.exception.FileNotFoundException;
import nuri.image_server.domain.file.domain.repository.FileEntityRepository;
import nuri.image_server.domain.secret_key.domain.exception.SecretKeyNotFoundException;
import nuri.image_server.domain.secret_key.domain.repository.SecretKeyEntityRepository;
import nuri.image_server.global.exception.NuriException;
import nuri.image_server.global.properties.FileProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileEntityRepository fileEntityRepository;
    private final SecretKeyEntityRepository secretKeyEntityRepository;
    private final FileProperties fileProperties;

    public Resource readFile(UUID fileId) {
        FileEntity fileEntity = fileEntityRepository.findById(fileId).orElseThrow(FileNotFoundException::new);
        String path = fileProperties.getBasePath() + fileEntity.getId();
        try {
            Path filePath = Paths.get(path);
            return new UrlResource(filePath.toUri());
        } catch (IOException e) {
            throw new NuriException("파일을 읽는 도중 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<UUID> uploadFiles(String secretKey, List<MultipartFile> fileUploadRequestDtoList) {
        if(!secretKeyEntityRepository.existsBySecretKey(secretKey)) {
            throw new SecretKeyNotFoundException(secretKey);
        }

        String basePath = fileProperties.getBasePath();

        List<FileEntity> fileEntities = fileUploadRequestDtoList.stream().map(fileUploadRequestDto -> {
            if(fileProperties.getContents().stream().noneMatch(type -> type.equals(fileUploadRequestDto.getContentType()))) {
                throw new FileEmptyException();
            }

            FileEntity fileEntity = FileEntity.builder()
                    .type(fileUploadRequestDto.getContentType())
                    .build();

            String fileName = basePath + fileEntity.getId().toString();

            try (InputStream inputStream = fileUploadRequestDto.getInputStream()) {
                Path uploadPath = Paths.get(fileName);
                Files.copy(inputStream, uploadPath, StandardCopyOption.REPLACE_EXISTING);
                return fileEntity;
            } catch (IOException e) {
                throw new NuriException("입출력에 관한 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }).toList();

        fileEntityRepository.saveAll(fileEntities);

        return fileEntities.stream().map(FileEntity::getId).toList();
    }

    public void deleteFiles(String secretKey, List<UUID> uuids) {
        if(!secretKeyEntityRepository.existsBySecretKey(secretKey)) {
            throw new SecretKeyNotFoundException(secretKey);
        }

        if(!fileEntityRepository.existsAllById(uuids)) {
            throw new FileNotFoundException();
        }

        for(UUID uuid : uuids) {
            String filePath = fileProperties.getBasePath() + uuid;

            File file = new File(filePath);
            if(file.exists() && !file.delete()) {
                throw new NuriException("파일 삭제에 실패하였습니다.",  HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        fileEntityRepository.deleteAllById(uuids);
    }
}
