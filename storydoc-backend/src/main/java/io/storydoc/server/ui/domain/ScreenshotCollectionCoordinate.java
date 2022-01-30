package io.storydoc.server.ui.domain;

import io.storydoc.server.storydoc.domain.ArtifactBlockCoordinate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScreenshotCollectionCoordinate {

    ArtifactBlockCoordinate blockCoordinate;
    ScreenShotCollectionId screenShotCollectionId;

    static public ScreenshotCollectionCoordinate of( ArtifactBlockCoordinate blockCoordinate, ScreenShotCollectionId screenShotCollectionId) {
        return new ScreenshotCollectionCoordinate(blockCoordinate, screenShotCollectionId);
    }
}
