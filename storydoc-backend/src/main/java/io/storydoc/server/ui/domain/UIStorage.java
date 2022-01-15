package io.storydoc.server.ui.domain;

import io.storydoc.server.storydoc.domain.ArtifactBlockCoordinate;
import io.storydoc.server.ui.infra.json.MockUI;
import io.storydoc.server.workspace.domain.ResourceUrn;

import java.io.InputStream;

public interface UIStorage {
    void createMockUI(ArtifactBlockCoordinate coordinate, MockUIId uiId, String name);

    MockUI loadMockUI(ArtifactBlockCoordinate coordinate, MockUIId id);
    ResourceUrn getUIArtifactResourceUrn(ArtifactBlockCoordinate coordinate, MockUIId uiId);
    ResourceUrn getScreenshotArtifactResourceUrn(ArtifactBlockCoordinate coordinate, ScreenshotId screenshotId);

    void addScreenshot(ArtifactBlockCoordinate coordinate, MockUIId mockUIId, ScreenshotId screenshotId);

    void createScreenshot(ArtifactBlockCoordinate coordinate, ScreenshotId screenshotId, InputStream inputStream, String name);
}
