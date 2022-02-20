package io.storydoc.server.ui.domain;

import io.storydoc.server.storydoc.domain.BlockCoordinate;
import org.springframework.stereotype.Service;

@Service
public class UIDomainService {

    private final UIStorage storage;

    public UIDomainService(UIStorage storage) {
        this.storage = storage;
    }

    public void createUIScenario(UIScenarioCoordinate scenarioCoordinate, String name) {
        storage.createUIScenario(scenarioCoordinate, name);
    }

    public UIScenario getUIScenario(UIScenarioCoordinate scenarioCoordinate) {
        return UIScenario.builder()
                .coordinate(scenarioCoordinate)
                .uiStorage(storage)
                .build();
    }

    public void createScreenShotCollection(BlockCoordinate coordinate, ScreenShotCollectionId screenShotCollectionId, String name) {
        storage.createScreenshotCollection(coordinate, screenShotCollectionId, name);
    }

    public ScreenShotCollection getScreenShotCollection(ScreenshotCollectionCoordinate collectionCoordinate) {
        return ScreenShotCollection.builder()
            .collectionCoordinate(collectionCoordinate)
            .uiStorage(storage)
            .build();
    }
}
