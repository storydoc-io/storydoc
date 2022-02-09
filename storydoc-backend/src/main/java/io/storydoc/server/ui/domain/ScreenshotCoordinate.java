package io.storydoc.server.ui.domain;

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
public class ScreenshotCoordinate {

    ScreenshotCollectionCoordinate collectionCoordinate;
    ScreenShotId screenShotId;

    static public ScreenshotCoordinate of(StoryDocId storyDocId, BlockId blockId, ScreenShotCollectionId screenShotCollectionId, ScreenShotId screenShotId) {
        return of(BlockCoordinate.of(storyDocId, blockId), screenShotCollectionId, screenShotId);
    }

    static public ScreenshotCoordinate of(BlockCoordinate blockCoordinate, ScreenShotCollectionId screenShotCollectionId, ScreenShotId screenShotId) {
        return of(ScreenshotCollectionCoordinate.of(blockCoordinate, screenShotCollectionId), screenShotId);
    }

    static public ScreenshotCoordinate of(ScreenshotCollectionCoordinate collectionCoordinate, ScreenShotId screenShotId) {
        return new ScreenshotCoordinate(collectionCoordinate, screenShotId);
    }

}
