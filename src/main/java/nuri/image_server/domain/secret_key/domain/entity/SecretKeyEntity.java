package nuri.image_server.domain.secret_key.domain.entity;

import jakarta.persistence.*;
import nuri.image_server.global.entity.BaseEntity;

@Entity
public class SecretKeyEntity extends BaseEntity {
    @Column(name = "secret_key", unique = true, nullable = false)
    private String secretKey;
}
