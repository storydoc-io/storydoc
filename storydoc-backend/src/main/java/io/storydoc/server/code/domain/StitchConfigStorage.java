package io.storydoc.server.code.domain;

import io.storydoc.server.code.infra.model.StitchConfig;

public interface StitchConfigStorage {
    void createStitchConfig(StitchConfigCoordinate coordinate, String name);

    StitchConfig load(StitchConfigCoordinate coordinate);

    void setPath(StitchConfigCoordinate coordinate, String path);
}
