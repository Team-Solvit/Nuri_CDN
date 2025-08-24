package nuri.image_server.global.config;


import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationPropertiesScan(basePackages = "nuri.image_server.global.properties")
public class PropertiesConfig {
}