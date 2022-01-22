package io.storydoc.server.ui.app;

import io.storydoc.server.storydoc.domain.ArtifactBlockCoordinate;
import io.storydoc.server.ui.domain.MockUIId;
import io.storydoc.server.ui.domain.ScreenShotCollectionId;
import io.storydoc.server.ui.domain.ScreenShotId;

import java.io.InputStream;

public interface UIService {
    ScreenShotCollectionId createScreenShotCollection(ArtifactBlockCoordinate coordinate, String name);
    ScreenShotId uploadScreenShotToCollection(ArtifactBlockCoordinate coordinate, ScreenShotCollectionId screenshotCollectionId, InputStream inputStream, String screenshot_name);

    MockUIId createMockUI(ArtifactBlockCoordinate coordinate, String name);
    void addScreenShot(ArtifactBlockCoordinate coordinate, MockUIId mockUIId, ScreenShotId screenshotId);

    @Deprecated
    ScreenShotId uploadScreenShot(ArtifactBlockCoordinate coordinate, InputStream inputStream, String name);
}
