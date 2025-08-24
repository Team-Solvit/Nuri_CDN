package nuri.image_server.global.entity.exception;

import nuri.image_server.global.exception.NuriException;
import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends NuriException {
    public EntityNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
