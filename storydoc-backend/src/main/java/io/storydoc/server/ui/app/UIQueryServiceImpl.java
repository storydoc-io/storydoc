package io.storydoc.server.ui.app;

import io.storydoc.server.storydoc.app.StoryDocQueryService;
import io.storydoc.server.storydoc.app.dto.ArtifactDTO;
import io.storydoc.server.storydoc.domain.ArtifactBlockCoordinate;
import io.storydoc.server.storydoc.domain.ArtifactMetaData;
import io.storydoc.server.ui.domain.*;
import io.storydoc.server.ui.infra.json.MockUI;
import io.storydoc.server.ui.infra.json.Screenshot;
import io.storydoc.server.workspace.domain.WorkspaceException;
import org.springframework.stereotype.Service;

import java.io.InputStream;
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
        List<ScreenShotCollectionId> associatedCollectionIds = storyDocQueryService.getArtifactsByType(coordinate, ScreenShotCollection.ARTIFACT_TYPE).stream()
            .map((a)->ScreenShotCollectionId.fromString(a.getId()))
            .collect(Collectors.toList());
        return toDTO(uiStorage.loadMockUI(coordinate, mockUIId), metaData, associatedCollectionIds);
    }

    @Override
    public ScreenShotCollectionDTO getScreenShotCollection(ArtifactBlockCoordinate coordinate, ScreenShotCollectionId screenShotCollectionId) {
        ArtifactDTO artifactDTO = storyDocQueryService.getArtifact(coordinate, screenShotCollectionId.asArtifactId());
        return ScreenShotCollectionDTO.builder()
            .screenShots(artifactDTO.getItems().stream()
                .map(item -> ScreenShotDTO.builder()
                    .id(ScreenShotId.fromString(item.getId()))
                    .name(item.getName())
                    .imgURL(item.getId())
                    .build())
                .collect(Collectors.toList())
            )
            .build();
    }

    @Override
    public ScreenShotDTO getScreenshotDTO(ArtifactBlockCoordinate coordinate, MockUIId id) {
        return null; //toDTO(uiStorage.loadMockUI(coordinate, id));
    }

    @Override
    public List<ScreenShotId> getScreenshots(ArtifactBlockCoordinate coordinate) {

        return storyDocQueryService.getArtifactsByType(coordinate, io.storydoc.server.ui.domain.Screenshot.ARTIFACT_TYPE).stream()
                .map(artifactId -> new ScreenShotId(artifactId.getId()))
                .collect(Collectors.toList());
    }

    private MockUIDTO toDTO(MockUI mockUI, ArtifactMetaData metaData, List<ScreenShotCollectionId> associatedCollectionIds) {
        return MockUIDTO.builder()
                .id(MockUIId.fromString(mockUI.getId()))
                .name(metaData.getName())
                .screenshots(mockUI.getScreenshots().stream()
                    .map( (screenshot) -> toDTO(screenshot))
                    .collect(Collectors.toList())
                )
                .associatedCollections(associatedCollectionIds)
                .build();
    }

    private ScreenShotDTO toDTO(Screenshot screenshot) {
        return ScreenShotDTO.builder()
                .id(new ScreenShotId(screenshot.getScreenshotId()))
                .build();
    }

    @Override
    public InputStream getScreenshot(ArtifactBlockCoordinate coordinate, ScreenShotCollectionId screenShotCollectionId, ScreenShotId screenShotId) throws WorkspaceException {
        return storyDocQueryService.getBinaryFromCollection(coordinate, screenShotCollectionId.asArtifactId(), screenShotId.asItemId());
    }
}
