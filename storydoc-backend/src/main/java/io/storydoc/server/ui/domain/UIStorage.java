package io.storydoc.server.ui.domain;

import io.storydoc.server.storydoc.domain.BlockCoordinate;
import io.storydoc.server.timeline.domain.TimeLineCoordinate;
import io.storydoc.server.timeline.domain.TimeLineItemId;
import io.storydoc.server.ui.domain.action.CreateScreenShotCollectionArtifactAction;
import io.storydoc.server.ui.domain.action.UploadScreenShotToCollectionAction;
import io.storydoc.server.ui.infra.json.UIScenario;
import io.storydoc.server.workspace.domain.ResourceUrn;

public interface UIStorage {

    void createScreenshotCollection(CreateScreenShotCollectionArtifactAction action);
    void uploadScreenShot(UploadScreenShotToCollectionAction action);
    ResourceUrn getScreenshotUrn(BlockCoordinate blockCoordinate, ScreenShotId screenshotId);

    void createUIScenario(UIScenarioCoordinate scenarioCoordinate, String name);
    UIScenario loadUIScenario(UIScenarioCoordinate scenarioCoordinate);
    void addScreenshot(UIScenarioCoordinate scenarioCoordinate, ScreenshotCoordinate screenshotCoordinate, TimeLineItemId timeLineItemId);
    void setTimeLine(UIScenarioCoordinate coordinate, TimeLineCoordinate timeLineCoordinate);
    ResourceUrn getUIScenarioUrn(UIScenarioCoordinate scenarioCoordinate);

}
