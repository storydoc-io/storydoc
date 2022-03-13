package io.storydoc.server.ui.app.screendesign;

import io.storydoc.server.ui.domain.screendesign.ScreenDesignCoordinate;

import java.util.List;

public interface ScreenDesignQueryService {

    ScreenDesignDTO getScreenDesign(ScreenDesignCoordinate coordinate);

    List<ComponentDescriptorDTO> getComponentPalette();

    ComponentDescriptorDTO getDescriptor(String type);
}
