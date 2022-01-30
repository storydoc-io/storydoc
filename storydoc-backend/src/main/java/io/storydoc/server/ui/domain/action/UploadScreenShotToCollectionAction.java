package io.storydoc.server.ui.domain.action;

import io.storydoc.server.ui.domain.ScreenShotId;
import io.storydoc.server.ui.domain.ScreenshotCollectionCoordinate;
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
    private ScreenshotCollectionCoordinate collectionCoordinate;
    private InputStream inputStream;
    private String name;
    private ScreenShotId screenshotId;
}
