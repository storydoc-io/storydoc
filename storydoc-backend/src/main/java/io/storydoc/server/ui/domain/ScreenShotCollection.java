package io.storydoc.server.ui.domain;

import io.storydoc.server.ui.domain.action.UploadScreenShotToCollectionAction;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class ScreenShotCollection {
    static public String ARTIFACT_TYPE = ScreenShotCollection.class.getName();

    private final ScreenshotCollectionCoordinate collectionCoordinate;
    private final UIStorage uiStorage;

    public void uploadScreenShot(UploadScreenShotToCollectionAction action) {
        uiStorage.uploadScreenShot(action);
    }
}
