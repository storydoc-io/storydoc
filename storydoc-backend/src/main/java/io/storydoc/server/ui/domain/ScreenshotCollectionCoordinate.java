package io.storydoc.server.ui.domain;

import io.storydoc.server.storydoc.domain.ArtifactCoordinate;
import io.storydoc.server.storydoc.domain.BlockCoordinate;
import io.storydoc.server.storydoc.domain.BlockId;
import io.storydoc.server.storydoc.domain.StoryDocId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScreenshotCollectionCoordinate {

    BlockCoordinate blockCoordinate;
    ScreenShotCollectionId screenShotCollectionId;

    static public ScreenshotCollectionCoordinate of(StoryDocId storyDocId, BlockId blockId, ScreenShotCollectionId screenShotCollectionId) {
        return of(BlockCoordinate.of(storyDocId, blockId), screenShotCollectionId);
    }

    static public ScreenshotCollectionCoordinate of(BlockCoordinate blockCoordinate, ScreenShotCollectionId screenShotCollectionId) {
        return new ScreenshotCollectionCoordinate(blockCoordinate, screenShotCollectionId);
    }

    public ArtifactCoordinate asArtifactCoordinate() {
        return ArtifactCoordinate.of(screenShotCollectionId.asArtifactId(), blockCoordinate);
    }
}
