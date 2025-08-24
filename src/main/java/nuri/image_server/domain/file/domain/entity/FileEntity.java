package nuri.image_server.domain.file.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuri.image_server.global.entity.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileEntity extends BaseEntity {
    @Column(name = "writer")
    private String writerId;

    @Column(name = "type")
    private String type;

    @Builder
    public FileEntity(String type, String writerId) {
        this.type = type;
        this.writerId = writerId;
    }
}
