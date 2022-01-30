package io.storydoc.server.ui.app;

import io.storydoc.server.infra.IDGenerator;
import io.storydoc.server.storydoc.domain.ArtifactBlockCoordinate;
import io.storydoc.server.timeline.domain.TimeLineCoordinate;
import io.storydoc.server.timeline.domain.TimeLineItemId;
import io.storydoc.server.ui.domain.*;
import io.storydoc.server.ui.domain.action.CreateScreenShotCollectionArtifactAction;
import io.storydoc.server.ui.domain.action.UploadScreenShotToCollectionAction;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class UIServiceImpl implements UIService {

    private final IDGenerator idGenerator;

    private final UIDomainService domainService;

    public UIServiceImpl(IDGenerator idGenerator, UIDomainService domainService) {
        this.idGenerator = idGenerator;
        this.domainService = domainService;
    }

    @Override
    public UIScenarioId createUIScenario(ArtifactBlockCoordinate coordinate, String name) {
        UIScenarioId uiId = new UIScenarioId(idGenerator.generateID("MOCKUI"));
        domainService.createUIScenario(UIScenarioCoordinate.of(coordinate, uiId), name);
        return uiId;
    }

    @Override
    public ScreenShotCollectionId createScreenShotCollection(ArtifactBlockCoordinate coordinate, String name) {
        CreateScreenShotCollectionArtifactAction action = CreateScreenShotCollectionArtifactAction.builder()
            .coordinate(coordinate)
            .name(name)
            .build();
        domainService.createScreenShotCollection(action);
        return action.getCollectionId();
    }

    @Override
    public ScreenShotId uploadScreenShotToCollection(ScreenshotCollectionCoordinate collectionCoordinate, InputStream inputStream, String name) {
        UploadScreenShotToCollectionAction action = UploadScreenShotToCollectionAction.builder()
            .collectionCoordinate(collectionCoordinate)
            .name(name)
            .inputStream(inputStream)
            .build();
        ScreenShotCollection screenShotCollection = domainService.getScreenShotCollection(collectionCoordinate);
        screenShotCollection.uploadScreenShot(action);
        return action.getScreenshotId();
    }

    @Override
    public void addScreenShotToUIScenario(UIScenarioCoordinate scenarioCoordinate, ScreenshotCoordinate screenshotCoordinate, TimeLineItemId timeLineItemId) {
       UIScenario uiScenario = domainService.getUIScenario(scenarioCoordinate);
       uiScenario.addScreenshot(screenshotCoordinate, timeLineItemId);
    }

    @Override
    public void setTimeLineForUIScenario(UIScenarioCoordinate scenarioCoordinate, TimeLineCoordinate timeLineCoordinate) {
        UIScenario uiScenario = domainService.getUIScenario(scenarioCoordinate);
        uiScenario.setTimeLine(timeLineCoordinate);
    }
}
