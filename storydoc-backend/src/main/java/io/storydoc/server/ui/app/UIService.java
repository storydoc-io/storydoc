package io.storydoc.server.ui.app;

import io.storydoc.server.storydoc.domain.ArtifactBlockCoordinate;
import io.storydoc.server.ui.domain.MockUIId;
import io.storydoc.server.ui.domain.ScreenshotId;

import java.io.InputStream;

public interface UIService {
    MockUIId createMockUI(ArtifactBlockCoordinate coordinate, String name);
    void addScreenShot(ArtifactBlockCoordinate coordinate, MockUIId mockUIId, ScreenshotId screenshotId);
    ScreenshotId uploadScreenShot(ArtifactBlockCoordinate coordinate, InputStream inputStream, String name);
}
