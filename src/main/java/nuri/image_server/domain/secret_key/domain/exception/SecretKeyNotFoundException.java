package nuri.image_server.domain.secret_key.domain.exception;

import nuri.image_server.global.entity.exception.EntityNotFoundException;

public class SecretKeyNotFoundException extends EntityNotFoundException {
    public SecretKeyNotFoundException(String secretKey) {
        super("시크릿 키가 " + secretKey + "인 시크릿 키가 존재하지 않습니다.");
    }
}
