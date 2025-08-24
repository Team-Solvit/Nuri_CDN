package nuri.image_server.global.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "file")
public class FileProperties {
    private final String basePath;
    private final List<String> contents;
}
