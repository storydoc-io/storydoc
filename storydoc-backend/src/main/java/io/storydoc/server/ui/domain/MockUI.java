package io.storydoc.server.ui.domain;

import io.storydoc.server.storydoc.domain.ArtifactBlockCoordinate;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class MockUI {

    static public String ARTIFACT_TYPE = MockUI.class.getName();

    private ArtifactBlockCoordinate coordinate;
    private MockUIId mockUIId;
    private UIStorage uiStorage;


    public void addScreenshot(ScreenShotId screenshotId) {
        uiStorage.addScreenshot(coordinate, mockUIId, screenshotId);
    }
}
