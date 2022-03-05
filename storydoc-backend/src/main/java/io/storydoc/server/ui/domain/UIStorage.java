package io.storydoc.server.ui.domain;

import io.storydoc.server.storydoc.domain.BlockCoordinate;
import io.storydoc.server.timeline.domain.TimeLineItemId;
import io.storydoc.server.timeline.domain.TimeLineModelCoordinate;
import io.storydoc.server.ui.infra.json.UIScenario;
import io.storydoc.server.workspace.domain.ResourceUrn;

import java.io.InputStream;

public interface UIStorage {

    void createScreenshotCollection(BlockCoordinate coordinate, ScreenShotCollectionId screenShotCollectionId, String name);
    ScreenShotId uploadScreenShot(ScreenshotCollectionCoordinate collectionCoordinate, InputStream inputStream, String name);
    ResourceUrn getScreenshotUrn(ScreenshotCollectionCoordinate collectionCoordinate, ScreenShotId screenshotId);

    void createUIScenario(UIScenarioCoordinate scenarioCoordinate, String name);
    ResourceUrn getUIScenarioUrn(UIScenarioCoordinate scenarioCoordinate);
    UIScenario loadUIScenario(UIScenarioCoordinate scenarioCoordinate);
    void setTimeLineModel(UIScenarioCoordinate coordinate, TimeLineModelCoordinate timeLineModelCoordinate);
    void addScreenshot(UIScenarioCoordinate scenarioCoordinate, ScreenshotCoordinate screenshotCoordinate, TimeLineItemId timeLineItemId);
    void removeScreenshot(UIScenarioCoordinate coordinate, TimeLineItemId timeLineItemId);

}
