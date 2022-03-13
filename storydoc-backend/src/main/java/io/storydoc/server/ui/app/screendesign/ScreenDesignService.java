package io.storydoc.server.ui.app.screendesign;

import io.storydoc.server.storydoc.domain.BlockCoordinate;
import io.storydoc.server.ui.domain.screendesign.SDComponentId;
import io.storydoc.server.ui.domain.screendesign.ScreenDesignCoordinate;

public interface ScreenDesignService {

    ScreenDesignCoordinate createScreenDesign(BlockCoordinate blockCoordinate, String name);
    SDComponentId addComponent(ScreenDesignCoordinate screenDesignCoordinate, SDComponentId rootContainerId, String type, int x, int y);
}
