package io.storydoc.server.code.domain;

import lombok.Builder;

@Builder
public class StitchConfig {
    public static final String ARTIFACT_TYPE = StitchConfig.class.getName();

    private StitchConfigId sourceCodeConfigId;
    private StitchConfigStorage storage;

    public void setPath(StitchConfigCoordinate coordinate, String path) {
        storage.setPath(coordinate, path);
    }
}
