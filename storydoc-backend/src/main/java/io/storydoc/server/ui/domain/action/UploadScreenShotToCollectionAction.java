package io.storydoc.server.ui.domain.action;

import io.storydoc.server.storydoc.domain.ArtifactBlockCoordinate;
import io.storydoc.server.ui.domain.ScreenShotCollectionId;
import io.storydoc.server.ui.domain.ScreenShotId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadScreenShotToCollectionAction {
    private ArtifactBlockCoordinate coordinate;
    private ScreenShotCollectionId collectionId;
    private InputStream inputStream;
    private String name;
    private ScreenShotId screenshotId;
}
