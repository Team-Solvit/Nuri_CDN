package nuri.image_server.domain.file.domain.exception;

import nuri.image_server.global.exception.NuriException;
import org.springframework.http.HttpStatus;

public class FileEmptyException extends NuriException {
    public FileEmptyException() {
        super("파일이 빈 파일입니다.", HttpStatus.BAD_REQUEST);
    }
}
