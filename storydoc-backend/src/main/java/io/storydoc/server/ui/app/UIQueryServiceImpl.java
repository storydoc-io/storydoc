package io.storydoc.server.ui.app;

import io.storydoc.server.storydoc.app.StoryDocQueryService;
import io.storydoc.server.storydoc.app.dto.ArtifactDTO;
import io.storydoc.server.storydoc.app.dto.StoryDocSummaryDTO;
import io.storydoc.server.storydoc.domain.ArtifactMetaData;
import io.storydoc.server.storydoc.domain.StoryDocId;
import io.storydoc.server.timeline.domain.TimeLineItemId;
import io.storydoc.server.ui.domain.*;
import io.storydoc.server.ui.infra.json.ScreenshotTimeLineItem;
import io.storydoc.server.ui.infra.json.UIScenario;
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
    public UIScenarioDTO getUIScenario(UIScenarioCoordinate scenarioCoordinate) {
        ArtifactMetaData metaData = getUIScenarioMetaData(scenarioCoordinate);
        List<ScreenshotCollectionCoordinate> defaultAssociatedScreenshotCollections = storyDocQueryService.getArtifactsByType(scenarioCoordinate.getBlockCoordinate(), ScreenShotCollection.ARTIFACT_TYPE).stream()
                .map(artifactId -> ScreenshotCollectionCoordinate.builder()
                        .blockCoordinate(scenarioCoordinate.getBlockCoordinate())
                        .screenShotCollectionId(ScreenShotCollectionId.fromString(artifactId.getId()))
                        .build())
                .collect(Collectors.toList());

        List<ScreenshotCollectionSummaryDTO> collectionSummaryDTOList = defaultAssociatedScreenshotCollections.stream()
            .map((collectionCoordinate)-> ScreenshotCollectionSummaryDTO.builder()
                .name(getScreenshotCollectionMetaData(collectionCoordinate).getName())
                .collectionCoordinate(collectionCoordinate)
                .build()
            )
            .collect(Collectors.toList());
        return toDTO(scenarioCoordinate, uiStorage.loadUIScenario(scenarioCoordinate), metaData, collectionSummaryDTOList);
    }

    private ArtifactMetaData getUIScenarioMetaData(UIScenarioCoordinate scenarioCoordinate) {
        return storyDocQueryService.getArtifactMetaData(scenarioCoordinate.getBlockCoordinate(), scenarioCoordinate.getUiScenarioId().asArtifactId());
    }

    private ArtifactMetaData getScreenshotCollectionMetaData(ScreenshotCollectionCoordinate collectionCoordinate) {
        return storyDocQueryService.getArtifactMetaData(collectionCoordinate.getBlockCoordinate(), collectionCoordinate.getScreenShotCollectionId().asArtifactId());
    }

    @Override
    public ScreenShotCollectionDTO getScreenShotCollection(ScreenshotCollectionCoordinate collectionCoordinate) {
        ArtifactDTO artifactDTO = storyDocQueryService.getArtifact(collectionCoordinate.getBlockCoordinate(), collectionCoordinate.getScreenShotCollectionId().asArtifactId());
        return ScreenShotCollectionDTO.builder()
            .storyDocSummary(getStoryDocSummary(collectionCoordinate.getBlockCoordinate().getStoryDocId()))
            .name(artifactDTO.getName())
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

    private StoryDocSummaryDTO getStoryDocSummary(StoryDocId storyDocId) {
        return storyDocQueryService.getStoryDocSummary(storyDocId);
    }

    private UIScenarioDTO toDTO(UIScenarioCoordinate scenarioCoordinate, UIScenario uiScenario, ArtifactMetaData metaData, List<ScreenshotCollectionSummaryDTO> associatedCollectionRefs) {
        return UIScenarioDTO.builder()
                .id(UIScenarioId.fromString(uiScenario.getId()))
                .storyDocSummary(getStoryDocSummary(scenarioCoordinate.getBlockCoordinate().getStoryDocId()))
                .name(metaData.getName())
                .timeLineModelCoordinate(uiScenario.getTimeLineModelCoordinate())
                .timeLineId(uiScenario.getTimeLineId())
                .screenshots(uiScenario.getScreenshots().stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList())
                )
                .associatedCollections(associatedCollectionRefs)
                .build();
    }

    private ScreenShotTimeLineItemDTO toDTO(ScreenshotTimeLineItem screenshot) {
        return ScreenShotTimeLineItemDTO.builder()
                .screenshotCoordinate(screenshot.getScreenshotCoordinate())
                .timeLineItemId(TimeLineItemId.fromString(screenshot.getTimeLineItemId()))
                .build();
    }

    @Override
    public InputStream getScreenshot(ScreenshotCollectionCoordinate collectionCoordinate, ScreenShotId screenShotId) throws WorkspaceException {
        return storyDocQueryService.getBinaryFromCollection(collectionCoordinate.asArtifactCoordinate(), screenShotId.asItemId());
    }
}
