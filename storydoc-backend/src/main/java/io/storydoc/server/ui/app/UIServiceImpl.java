package io.storydoc.server.ui.app;

import io.storydoc.server.infra.IDGenerator;
import io.storydoc.server.storydoc.domain.ArtifactBlockCoordinate;
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
    public MockUIId createMockUI(ArtifactBlockCoordinate coordinate, String name) {
        MockUIId uiId = new MockUIId(idGenerator.generateID("MOCKUI"));
        domainService.createMockUI(coordinate, uiId, name);
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
    public ScreenShotId uploadScreenShotToCollection(ArtifactBlockCoordinate coordinate, ScreenShotCollectionId collectionId, InputStream inputStream, String name) {
        UploadScreenShotToCollectionAction action = UploadScreenShotToCollectionAction.builder()
            .coordinate(coordinate)
            .collectionId(collectionId)
            .name(name)
            .inputStream(inputStream)
            .build();
        ScreenShotCollection screenShotCollection = domainService.getScreenShotCollection(coordinate, collectionId);
        screenShotCollection.uploadScreenShot(action);
        return action.getScreenshotId();
    }

    @Override
    public ScreenShotId uploadScreenShot(ArtifactBlockCoordinate coordinate, InputStream inputStream, String name) {
        ScreenShotId screenshotId = new ScreenShotId(idGenerator.generateID("SCREENSHOT"));
        domainService.createScreenshot(coordinate, screenshotId, inputStream, name);
        return screenshotId;
    }

    @Override
    public void addScreenShot(ArtifactBlockCoordinate coordinate, MockUIId mockUIId, ScreenShotId screenshotId) {
       MockUI mockUI = domainService.getMockUI(coordinate, mockUIId);
       mockUI.addScreenshot(screenshotId);
    }
}
