package io.storydoc.server.ui.app;

import io.storydoc.server.infra.IDGenerator;
import io.storydoc.server.storydoc.domain.ArtifactBlockCoordinate;
import io.storydoc.server.ui.domain.MockUI;
import io.storydoc.server.ui.domain.MockUIId;
import io.storydoc.server.ui.domain.ScreenshotId;
import io.storydoc.server.ui.domain.UIDomainService;
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
    public ScreenshotId uploadScreenShot(ArtifactBlockCoordinate coordinate, InputStream inputStream, String name) {
        ScreenshotId screenshotId = new ScreenshotId(idGenerator.generateID("SCREENSHOT"));
        domainService.createScreenshot(coordinate, screenshotId, inputStream, name);
        return screenshotId;
    }

    @Override
    public void addScreenShot(ArtifactBlockCoordinate coordinate, MockUIId mockUIId, ScreenshotId screenshotId) {
       MockUI mockUI = domainService.getMockUI(coordinate, mockUIId);
       mockUI.addScreenshot(screenshotId);
    }
}
