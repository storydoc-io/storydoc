package io.storydoc.server.ui.app;

import io.storydoc.server.storydoc.domain.BlockCoordinate;
import io.storydoc.server.ui.domain.*;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class UITestUtils {

    private final UIService uiService;

    public UITestUtils(UIService uiService) {
        this.uiService = uiService;
    }

    public UIScenarioCoordinate createUIScenario(BlockCoordinate blockCoordinate) {
        UIScenarioId uiScenarioId = uiService.createUIScenario(blockCoordinate, "uiscenario");
        UIScenarioCoordinate scenarioCoordinate = UIScenarioCoordinate.of(blockCoordinate, uiScenarioId);
        return scenarioCoordinate;
    }

    public ScreenshotCollectionCoordinate createScreenshotCollection(BlockCoordinate blockCoordinate) {
        String screenshots_artifact_name = "screenshots";
        ScreenShotCollectionId screenShotCollectionId = uiService.createScreenShotCollection(blockCoordinate, screenshots_artifact_name);
        ScreenshotCollectionCoordinate collectionCoordinate = ScreenshotCollectionCoordinate.of(blockCoordinate, screenShotCollectionId);
        return collectionCoordinate;
    }

    public ScreenShotId upoadScreenShotToCollection(ScreenshotCollectionCoordinate collectionCoordinate) {
        String screenshot_name = "screenshot";
        InputStream inputStream = this.getClass().getResourceAsStream("dummy-image-10x10.png");
        return uiService.uploadScreenShotToCollection(collectionCoordinate, inputStream, screenshot_name);

    }
}
