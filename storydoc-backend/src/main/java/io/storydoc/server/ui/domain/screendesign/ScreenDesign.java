package io.storydoc.server.ui.domain.screendesign;

import io.storydoc.server.ui.app.screendesign.ComponentDescriptorDTO;

public class ScreenDesign {
    public static final String ARTIFACT_TYPE = ScreenDesign.class.getName();

    private final ScreenDesignId id;
    private final ScreenDesignStorage storage;

    public ScreenDesign(ScreenDesignId id, ScreenDesignStorage storage) {
        this.id = id;
        this.storage = storage;
    }

    public SDComponentId addComponent(ScreenDesignCoordinate screenDesignCoordinate, SDComponentId parentContainerId, String type, ComponentDescriptorDTO descriptorDTO, int x, int y) {
        return storage.addComponent(screenDesignCoordinate, parentContainerId, type, descriptorDTO, x, y);
    }

    public void moveComponent(ScreenDesignCoordinate screenDesignCoordinate, SDComponentId componentId, int x, int y) {
        storage.moveComponent(screenDesignCoordinate, componentId, x, y);
    }

    public void deleteComponent(ScreenDesignCoordinate screenDesignCoordinate, SDComponentId componentId) {
        storage.deleteComponent(screenDesignCoordinate, componentId);
    }

    public void renameComponent(ScreenDesignCoordinate screenDesignCoordinate, SDComponentId componentId, String name) {
        storage.renameComponent(screenDesignCoordinate, componentId, name);
    }
}
