package io.storydoc.server.ui.domain;

import io.storydoc.server.ui.domain.action.CreateScreenShotCollectionArtifactAction;
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

    public void createScreenShotCollection(CreateScreenShotCollectionArtifactAction action) {
        storage.createScreenshotCollection(action);
    }

    public ScreenShotCollection getScreenShotCollection(ScreenshotCollectionCoordinate collectionCoordinate) {
        return ScreenShotCollection.builder()
            .collectionCoordinate(collectionCoordinate)
            .uiStorage(storage)
            .build();
    }
}
