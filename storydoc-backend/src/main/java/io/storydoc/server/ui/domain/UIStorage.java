package io.storydoc.server.ui.domain;

import io.storydoc.server.storydoc.domain.BlockCoordinate;
import io.storydoc.server.timeline.domain.TimeLineCoordinate;
import io.storydoc.server.timeline.domain.TimeLineItemId;
import io.storydoc.server.ui.infra.json.UIScenario;
import io.storydoc.server.workspace.domain.ResourceUrn;

import java.io.InputStream;

public interface UIStorage {

    void createScreenshotCollection(BlockCoordinate coordinate, ScreenShotCollectionId screenShotCollectionId, String name);
    ScreenShotId uploadScreenShot(ScreenshotCollectionCoordinate collectionCoordinate, InputStream inputStream, String name);
    ResourceUrn getScreenshotUrn(ScreenshotCollectionCoordinate collectionCoordinate, ScreenShotId screenshotId);

    void createUIScenario(UIScenarioCoordinate scenarioCoordinate, String name);
    UIScenario loadUIScenario(UIScenarioCoordinate scenarioCoordinate);
    void addScreenshot(UIScenarioCoordinate scenarioCoordinate, ScreenshotCoordinate screenshotCoordinate, TimeLineItemId timeLineItemId);
    void setTimeLine(UIScenarioCoordinate coordinate, TimeLineCoordinate timeLineCoordinate);
    ResourceUrn getUIScenarioUrn(UIScenarioCoordinate scenarioCoordinate);

}
