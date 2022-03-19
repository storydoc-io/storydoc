package io.storydoc.server.ui.domain.screendesign;

import io.storydoc.server.ui.app.screendesign.ComponentDescriptorDTO;
import io.storydoc.server.ui.infra.json.screendesign.ScreenDesign;

public interface ScreenDesignStorage {
    void createScreenDesign(ScreenDesignCoordinate coordinate, String name);

    ScreenDesign loadScreenDesign(ScreenDesignCoordinate coordinate);

    SDComponentId addComponent(ScreenDesignCoordinate screenDesignCoordinate, SDComponentId parentContainerId, String type, ComponentDescriptorDTO descriptorDTO, int x, int y);

    void moveComponent(ScreenDesignCoordinate screenDesignCoordinate, SDComponentId componentId, int x, int y);

    void deleteComponent(ScreenDesignCoordinate screenDesignCoordinate, SDComponentId componentId);

    void renameComponent(ScreenDesignCoordinate screenDesignCoordinate, SDComponentId componentId, String name);
}

