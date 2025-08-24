package nuri.image_server.domain.file.domain.exception;

import nuri.image_server.global.entity.exception.EntityNotFoundException;

public class FileNotFoundException extends EntityNotFoundException {
    public FileNotFoundException() {
        super("파일을 찾을 수 없습니다.");
    }
}
