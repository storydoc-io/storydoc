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
public class ScreenshotCoordinate {

    ScreenshotCollectionCoordinate collectionCoordinate;
    ScreenShotId screenShotId;

    static public ScreenshotCoordinate of(ArtifactBlockCoordinate blockCoordinate, ScreenShotCollectionId screenShotCollectionId, ScreenShotId screenShotId) {
        return new ScreenshotCoordinate(ScreenshotCollectionCoordinate.of(blockCoordinate, screenShotCollectionId), screenShotId);
    }

    static public ScreenshotCoordinate of(ScreenshotCollectionCoordinate collectionCoordinate, ScreenShotId screenShotId) {
        return new ScreenshotCoordinate(collectionCoordinate, screenShotId);
    }

}
