package io.storydoc.server.code.domain;

import lombok.Builder;

@Builder
public class SourceCodeConfig {
    public static final String ARTIFACT_TYPE = SourceCodeConfig.class.getName();

    private SourceCodeConfigId sourceCodeConfigId;
    private SourceCodeConfigStorage storage;

    public void setSourcePath(SourceCodeConfigCoordinate coordinate, String path) {
        storage.setSourcePath(coordinate, path);
    }
}
