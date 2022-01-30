package io.storydoc.server.ui.app;

import io.storydoc.server.ui.domain.ScreenShotId;
import io.storydoc.server.ui.domain.ScreenshotCollectionCoordinate;
import io.storydoc.server.ui.domain.UIScenarioCoordinate;
import io.storydoc.server.workspace.domain.WorkspaceException;

import java.io.InputStream;

public interface UIQueryService {

    ScreenShotCollectionDTO getScreenShotCollection(ScreenshotCollectionCoordinate collectionCoordinate);

    UIScenarioDTO getUIScenario(UIScenarioCoordinate scenarioCoordinate);

    InputStream getScreenshot(ScreenshotCollectionCoordinate collectionCoordinate, ScreenShotId screenShotId) throws WorkspaceException;
}
