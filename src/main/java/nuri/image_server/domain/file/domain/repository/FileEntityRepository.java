package nuri.image_server.domain.file.domain.repository;

import nuri.image_server.domain.file.domain.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FileEntityRepository extends JpaRepository<FileEntity, UUID> {
    @Query("SELECT CASE WHEN COUNT(e.id) = :#{#uuids.size()} THEN true ELSE false END FROM FileEntity e WHERE e.id IN :uuids")
    boolean existsAllById(@Param("uuids") List<UUID> uuids);
}
