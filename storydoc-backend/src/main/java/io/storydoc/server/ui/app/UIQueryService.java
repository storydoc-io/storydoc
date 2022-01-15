package io.storydoc.server.ui.app;

import io.storydoc.server.storydoc.domain.ArtifactBlockCoordinate;
import io.storydoc.server.ui.domain.MockUIId;
import io.storydoc.server.ui.domain.ScreenshotId;

import java.util.List;

public interface UIQueryService {
    ScreenshotDTO getScreenshotDTO(ArtifactBlockCoordinate artifactBlockCoordinate, MockUIId id);
    MockUIDTO getMockUIDTO(ArtifactBlockCoordinate coordinate, MockUIId mockUIId);
    List<ScreenshotId> getScreenshots(ArtifactBlockCoordinate coordinate);

}
