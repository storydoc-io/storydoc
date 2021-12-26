package io.storydoc.server.folder.app;

import io.storydoc.server.config.StoryDocServerProperties;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
public class FolderSettings {

    private final  StoryDocServerProperties serverProperties;

    public FolderSettings(StoryDocServerProperties serverProperties) {
        this.serverProperties = serverProperties;
    }

    public Path getWorkspaceFolder() {
        return Path.of(serverProperties.getWorkspaceFolder());
    }
}
