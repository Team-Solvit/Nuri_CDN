package nuri.image_server.domain.secret_key.domain.repository;

import nuri.image_server.domain.secret_key.domain.entity.SecretKeyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SecretKeyEntityRepository extends JpaRepository<SecretKeyEntity, UUID> {
    boolean existsBySecretKey(String secretKey);
}
