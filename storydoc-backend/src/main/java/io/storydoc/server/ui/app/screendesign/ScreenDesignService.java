package io.storydoc.server.ui.app.screendesign;

import io.storydoc.server.storydoc.domain.BlockCoordinate;
import io.storydoc.server.ui.domain.screendesign.SDComponentId;
import io.storydoc.server.ui.domain.screendesign.ScreenDesignCoordinate;

public interface ScreenDesignService {

    ScreenDesignCoordinate createScreenDesign(BlockCoordinate blockCoordinate, String name);
    SDComponentId addComponent(ScreenDesignCoordinate screenDesignCoordinate, SDComponentId rootContainerId, String type, int x, int y);
    void moveComponent(ScreenDesignCoordinate screenDesignCoordinate, SDComponentId componentId, int x, int y);
    void deleteComponent(ScreenDesignCoordinate screenDesignCoordinate, SDComponentId componentId);
    void renameComponent(ScreenDesignCoordinate screenDesignCoordinate, SDComponentId componentId, String name);
}
