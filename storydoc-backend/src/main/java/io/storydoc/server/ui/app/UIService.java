package io.storydoc.server.ui.app;

import io.storydoc.server.storydoc.domain.ArtifactBlockCoordinate;
import io.storydoc.server.timeline.domain.TimeLineCoordinate;
import io.storydoc.server.timeline.domain.TimeLineItemId;
import io.storydoc.server.ui.domain.*;

import java.io.InputStream;

public interface UIService {
    ScreenShotCollectionId createScreenShotCollection(ArtifactBlockCoordinate coordinate, String name);
    ScreenShotId uploadScreenShotToCollection(ScreenshotCollectionCoordinate collectionCoordinate, InputStream inputStream, String screenshot_name);

    UIScenarioId createUIScenario(ArtifactBlockCoordinate coordinate, String name);
    void addScreenShotToUIScenario(UIScenarioCoordinate scenarioCoordinate, ScreenshotCoordinate screenshotCoordinate, TimeLineItemId timeLineItemId);
    void setTimeLineForUIScenario(UIScenarioCoordinate scenarioCoordinate, TimeLineCoordinate timeLineCoordinate);
}
