package io.storydoc.server.ui.app;

import io.storydoc.server.storydoc.domain.BlockCoordinate;
import io.storydoc.server.timeline.domain.TimeLineItemId;
import io.storydoc.server.timeline.domain.TimeLineModelCoordinate;
import io.storydoc.server.ui.domain.*;

import java.io.InputStream;

public interface UIService {
    ScreenShotCollectionId createScreenShotCollection(BlockCoordinate coordinate, String name);
    ScreenShotId uploadScreenShotToCollection(ScreenshotCollectionCoordinate collectionCoordinate, InputStream inputStream, String screenshot_name);
    void renameScreenShotInCollection(ScreenshotCoordinate of, String screenshot_name_after);
    void removeScreenShotFromCollection(ScreenshotCoordinate coordinate);

    UIScenarioId createUIScenario(BlockCoordinate coordinate, String name);

    void setTimeLineModelForUIScenario(UIScenarioCoordinate scenarioCoordinate, TimeLineModelCoordinate timeLineModelCoordinate);
    void addScreenShotToUIScenario(UIScenarioCoordinate scenarioCoordinate, ScreenshotCoordinate screenshotCoordinate, TimeLineItemId timeLineItemId);

    void removeScreenshotFromUIScenario(UIScenarioCoordinate scenarioCoordinate, TimeLineItemId timeLineItemId);
}
