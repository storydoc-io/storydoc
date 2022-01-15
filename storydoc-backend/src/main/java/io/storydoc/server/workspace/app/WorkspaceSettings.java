package io.storydoc.server.workspace.app;

import io.storydoc.server.config.StoryDocServerProperties;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
public class WorkspaceSettings {

    private final  StoryDocServerProperties serverProperties;

    public WorkspaceSettings(StoryDocServerProperties serverProperties) {
        this.serverProperties = serverProperties;
    }

    public Path getWorkspaceFolder() {
        return Path.of(serverProperties.getWorkspaceFolder());
    }
}
