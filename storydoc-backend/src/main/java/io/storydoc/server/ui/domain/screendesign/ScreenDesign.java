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
}
