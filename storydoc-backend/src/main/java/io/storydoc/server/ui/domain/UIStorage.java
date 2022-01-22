package io.storydoc.server.ui.domain;

import io.storydoc.server.storydoc.domain.ArtifactBlockCoordinate;
import io.storydoc.server.ui.domain.action.CreateScreenShotCollectionArtifactAction;
import io.storydoc.server.ui.domain.action.UploadScreenShotToCollectionAction;
import io.storydoc.server.ui.infra.json.MockUI;
import io.storydoc.server.workspace.domain.ResourceUrn;

import java.io.InputStream;

public interface UIStorage {
    void createMockUI(ArtifactBlockCoordinate coordinate, MockUIId uiId, String name);

    MockUI loadMockUI(ArtifactBlockCoordinate coordinate, MockUIId id);
    ResourceUrn getUIArtifactResourceUrn(ArtifactBlockCoordinate coordinate, MockUIId uiId);
    ResourceUrn getScreenshotArtifactResourceUrn(ArtifactBlockCoordinate coordinate, ScreenShotId screenshotId);

    void addScreenshot(ArtifactBlockCoordinate coordinate, MockUIId mockUIId, ScreenShotId screenshotId);

    void createScreenshot(ArtifactBlockCoordinate coordinate, ScreenShotId screenshotId, InputStream inputStream, String name);

    void createScreenshotCollection(CreateScreenShotCollectionArtifactAction action);

    void uploadScreenShot(UploadScreenShotToCollectionAction action);
}
