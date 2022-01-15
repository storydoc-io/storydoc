package io.storydoc.server.ui.domain;

import io.storydoc.server.storydoc.domain.ArtifactBlockCoordinate;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class UIDomainService {

    private final UIStorage storage;

    public UIDomainService(UIStorage storage) {
        this.storage = storage;
    }

    public void createMockUI(ArtifactBlockCoordinate coordinate, MockUIId uiId, String name) {
        storage.createMockUI(coordinate, uiId, name);
    }

    public MockUI getMockUI(ArtifactBlockCoordinate coordinate, MockUIId mockUIId) {
        return MockUI.builder()
                .coordinate(coordinate)
                .mockUIId(mockUIId)
                .uiStorage(storage)
                .build();
    }

    public void createScreenshot(ArtifactBlockCoordinate coordinate, ScreenshotId screenshotId, InputStream inputStream, String name) {
        storage.createScreenshot(coordinate, screenshotId, inputStream, name);
    }
}
