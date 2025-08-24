package nuri.image_server.domain.file.domain.exception;

import nuri.image_server.global.exception.NuriException;
import org.springframework.http.HttpStatus;

public class FileInvalidException extends NuriException {
    public FileInvalidException() {
        super("파일이 올바르지 않은 형식입니다.", HttpStatus.BAD_REQUEST);
    }
}
