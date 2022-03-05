package io.storydoc.server.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("io.storydoc")
@Data
public class StoryDocServerProperties {

    String workspaceFolder;

    long maxFileSize;
}
