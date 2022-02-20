package io.storydoc.server.ui.app;

import io.storydoc.server.infra.IDGenerator;
import io.storydoc.server.storydoc.domain.BlockCoordinate;
import io.storydoc.server.timeline.domain.TimeLineCoordinate;
import io.storydoc.server.timeline.domain.TimeLineItemId;
import io.storydoc.server.ui.domain.*;
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
    public UIScenarioId createUIScenario(BlockCoordinate coordinate, String name) {
        UIScenarioId uiId = new UIScenarioId(idGenerator.generateID(UIScenarioId.ID_PREFIX));
        domainService.createUIScenario(UIScenarioCoordinate.of(coordinate, uiId), name);
        return uiId;
    }

    @Override
    public ScreenShotCollectionId createScreenShotCollection(BlockCoordinate coordinate, String name) {
        ScreenShotCollectionId screenShotCollectionId = new ScreenShotCollectionId(idGenerator.generateID(ScreenShotCollectionId.ID_PREFIX));
        domainService.createScreenShotCollection(coordinate, screenShotCollectionId, name);
        return screenShotCollectionId;
    }

    @Override
    public ScreenShotId uploadScreenShotToCollection(ScreenshotCollectionCoordinate collectionCoordinate, InputStream inputStream, String name) {
        ScreenShotCollection screenShotCollection = domainService.getScreenShotCollection(collectionCoordinate);
        return screenShotCollection.uploadScreenShot(collectionCoordinate, inputStream, name);
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
