package io.storydoc.server.ui.domain;

import io.storydoc.server.storydoc.domain.ArtifactBlockCoordinate;
import io.storydoc.server.ui.domain.action.UploadScreenShotToCollectionAction;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class ScreenShotCollection {
    static public String ARTIFACT_TYPE = ScreenShotCollection.class.getName();

    private ArtifactBlockCoordinate coordinate;
    private ScreenShotCollectionId collectionId;
    private UIStorage uiStorage;

    public void uploadScreenShot(UploadScreenShotToCollectionAction action) {
        uiStorage.uploadScreenShot(action);
    }
}
