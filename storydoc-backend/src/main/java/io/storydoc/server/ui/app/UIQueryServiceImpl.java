package io.storydoc.server.ui.app;

import io.storydoc.server.storydoc.app.StoryDocQueryService;
import io.storydoc.server.storydoc.domain.ArtifactBlockCoordinate;
import io.storydoc.server.storydoc.domain.ArtifactMetaData;
import io.storydoc.server.ui.domain.MockUIId;
import io.storydoc.server.ui.domain.ScreenshotId;
import io.storydoc.server.ui.domain.UIStorage;
import io.storydoc.server.ui.infra.json.MockUI;
import io.storydoc.server.ui.infra.json.Screenshot;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UIQueryServiceImpl implements UIQueryService {

    private final UIStorage uiStorage;

    private final StoryDocQueryService storyDocQueryService;

    public UIQueryServiceImpl(UIStorage uiStorage, StoryDocQueryService storyDocQueryService) {
        this.uiStorage = uiStorage;
        this.storyDocQueryService = storyDocQueryService;
    }

    @Override
    public MockUIDTO getMockUIDTO(ArtifactBlockCoordinate coordinate, MockUIId mockUIId) {
        ArtifactMetaData metaData = storyDocQueryService.getArtifactMetaData(coordinate, mockUIId.asArtifactId());
        return toDTO(uiStorage.loadMockUI(coordinate, mockUIId), metaData);
    }

    @Override
    public ScreenshotDTO getScreenshotDTO(ArtifactBlockCoordinate coordinate, MockUIId id) {
        return null; //toDTO(uiStorage.loadMockUI(coordinate, id));
    }

    @Override
    public List<ScreenshotId> getScreenshots(ArtifactBlockCoordinate coordinate) {

        return storyDocQueryService.getArtifactsByType(coordinate, io.storydoc.server.ui.domain.Screenshot.ARTIFACT_TYPE).stream()
                .map(artifactId -> new ScreenshotId(artifactId.getId()))
                .collect(Collectors.toList());
    }

    private MockUIDTO toDTO(MockUI mockUI, ArtifactMetaData metaData) {
        return MockUIDTO.builder()
                .id(MockUIId.fromString(mockUI.getId()))
                .name(metaData.getName())
                .screenshots(mockUI.getScreenshots().stream()
                    .map( (screenshot) -> toDTO(screenshot))
                    .collect(Collectors.toList())
                )
                .build();
    }

    private ScreenshotDTO toDTO(Screenshot screenshot) {
        return ScreenshotDTO.builder()
                .id(new ScreenshotId(screenshot.getScreenshotId()))
                .build();
    }
}
