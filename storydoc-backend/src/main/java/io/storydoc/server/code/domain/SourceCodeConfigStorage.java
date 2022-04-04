package io.storydoc.server.code.domain;

import io.storydoc.server.code.infra.model.SourceCodeConfig;

public interface SourceCodeConfigStorage {
    void createSourceCodeConfig(SourceCodeConfigCoordinate coordinate, String name);

    SourceCodeConfig load(SourceCodeConfigCoordinate coordinate);
}
