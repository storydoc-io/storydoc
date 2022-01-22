package io.storydoc.server.ui.app;

import io.storydoc.server.storydoc.domain.ArtifactBlockCoordinate;
import io.storydoc.server.ui.domain.MockUIId;
import io.storydoc.server.ui.domain.ScreenShotCollectionId;
import io.storydoc.server.ui.domain.ScreenShotId;
import io.storydoc.server.workspace.domain.WorkspaceException;

import java.io.InputStream;
import java.util.List;

public interface UIQueryService {

    ScreenShotCollectionDTO getScreenShotCollection(ArtifactBlockCoordinate coordinate, ScreenShotCollectionId screenShotCollectionId);

    @Deprecated
    List<ScreenShotId> getScreenshots(ArtifactBlockCoordinate coordinate);
    @Deprecated
    ScreenShotDTO getScreenshotDTO(ArtifactBlockCoordinate artifactBlockCoordinate, MockUIId id);

    MockUIDTO getMockUIDTO(ArtifactBlockCoordinate coordinate, MockUIId mockUIId);

    InputStream getScreenshot(ArtifactBlockCoordinate coordinate, ScreenShotCollectionId screenShotCollectionId, ScreenShotId screenShotId) throws WorkspaceException;
}
