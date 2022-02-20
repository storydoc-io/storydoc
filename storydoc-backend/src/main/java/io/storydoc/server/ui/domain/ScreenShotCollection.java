package io.storydoc.server.ui.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.InputStream;

@Builder
@AllArgsConstructor
public class ScreenShotCollection {
    static public String ARTIFACT_TYPE = ScreenShotCollection.class.getName();

    private final ScreenshotCollectionCoordinate collectionCoordinate;
    private final UIStorage uiStorage;

    public ScreenShotId uploadScreenShot(ScreenshotCollectionCoordinate collectionCoordinate, InputStream inputStream, String name) {
        return uiStorage.uploadScreenShot(collectionCoordinate, inputStream, name);
    }
}
