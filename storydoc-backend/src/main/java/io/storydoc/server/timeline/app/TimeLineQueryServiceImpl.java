package io.storydoc.server.timeline.app;

import io.storydoc.server.storydoc.app.StoryDocQueryService;
import io.storydoc.server.storydoc.app.dto.StoryDocSummaryDTO;
import io.storydoc.server.storydoc.domain.BlockCoordinate;
import io.storydoc.server.storydoc.domain.ArtifactId;
import io.storydoc.server.storydoc.domain.ArtifactMetaData;
import io.storydoc.server.storydoc.domain.StoryDocId;
import io.storydoc.server.timeline.domain.*;
import io.storydoc.server.timeline.infra.TimeLine;
import io.storydoc.server.timeline.infra.TimeLineModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TimeLineQueryServiceImpl implements TimeLineQueryService {

    private final TimeLineStore timeLineStore;

    private final StoryDocQueryService storyDocQueryService;

    public TimeLineQueryServiceImpl(TimeLineStore timeLineStore, StoryDocQueryService storyDocQueryService) {
        this.timeLineStore = timeLineStore;
        this.storyDocQueryService = storyDocQueryService;
    }

    @Override
    public TimeLineModelDTO getTimeLineModel(TimeLineModelCoordinate timeLineModelCoordinate) {
        ArtifactMetaData metaData = storyDocQueryService.getArtifactMetaData(timeLineModelCoordinate.getBlockCoordinate(), timeLineModelCoordinate.getTimeLineModelId().asArtifactId());
        return toDTO(timeLineModelCoordinate, timeLineStore.loadTimeLineModel(timeLineModelCoordinate), metaData);
    }

    @Override
    public List<TimeLineModelSummaryDTO> getTimeLineModelSummaries(BlockCoordinate blockCoordinate) {
        List<ArtifactId> artifacts = storyDocQueryService.getArtifactsByType(blockCoordinate, io.storydoc.server.timeline.domain.TimeLineModel.ARTIFACT_TYPE);
        return artifacts.stream()
                .map(artifact -> {
                    ArtifactMetaData metaData = storyDocQueryService.getArtifactMetaData(blockCoordinate, ArtifactId.fromString(artifact.getId()));
                    return TimeLineModelSummaryDTO.builder()
                        .name(metaData.getName())
                        .timeLineModelCoordinate(TimeLineModelCoordinate.of(blockCoordinate, TimeLineModelId.fromString(artifact.getId())))
                        .build();
                })
                .collect(Collectors.toList());
    }

    private TimeLineModelDTO toDTO(TimeLineModelCoordinate timeLineModelCoordinate, TimeLineModel timeLineModel, ArtifactMetaData metaData) {
        return TimeLineModelDTO.builder()
            .timeLineModelCoordinate(timeLineModelCoordinate)
            .storyDocSummary(getStoryDocSummary(timeLineModelCoordinate.getBlockCoordinate().getStoryDocId()))
            .timeLines(timeLineModel.getTimeLines().entrySet().stream()
                    .collect(Collectors.toMap(
                       e -> e.getKey(),
                       e -> toDTO(e.getKey(), e.getValue())
                    )))
            .name(metaData.getName())
            .build();
    }

    private TimeLineDTO toDTO(String name, TimeLine timeLine) {
        return TimeLineDTO.builder()
                .timeLineId(TimeLineId.fromString(timeLine.getId()))
                .name(name)
                .items(timeLine.getItems().stream()
                    .map(i -> TimeLineItemDTO.builder()
                        .itemId(TimeLineItemId.fromString(i.getId()))
                        .description(i.getDescription())
                        .build())
                    .collect(Collectors.toList())
                )
                .build();
    }

    private StoryDocSummaryDTO getStoryDocSummary(StoryDocId storyDocId) {
        return storyDocQueryService.getStoryDocSummary(storyDocId);
    }

}
