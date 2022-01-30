package io.storydoc.server.timeline.infra;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.storydoc.server.storydoc.app.StoryDocQueryService;
import io.storydoc.server.storydoc.app.StoryDocService;
import io.storydoc.server.storydoc.domain.ArtifactId;
import io.storydoc.server.storydoc.domain.action.ArtifactLoadContext;
import io.storydoc.server.storydoc.domain.action.ArtifactSaveContext;
import io.storydoc.server.timeline.domain.TimeLineCoordinate;
import io.storydoc.server.timeline.domain.TimeLineItemId;
import io.storydoc.server.timeline.domain.TimeLineModelCoordinate;
import io.storydoc.server.timeline.domain.TimeLineStore;
import io.storydoc.server.workspace.domain.FolderURN;
import io.storydoc.server.workspace.domain.ResourceUrn;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TimeLineStoreImpl implements TimeLineStore {

    private ObjectMapper objectMapper;

    private final StoryDocService storyDocService;

    private final StoryDocQueryService storyDocQueryService;

    public TimeLineStoreImpl(StoryDocService storyDocService, StoryDocQueryService storyDocQueryService) {
        this.storyDocService = storyDocService;
        this.storyDocQueryService = storyDocQueryService;
        initObjectMapper();
    }

    private void initObjectMapper() {
        objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    @Override
    public void createTimeLineModel(TimeLineModelCoordinate timeLineModelCoordinate, TimeLineItemId defaultTimeLineId, String name) {
        TimeLine defaultTimeLine =  new TimeLine();
        defaultTimeLine.setId(defaultTimeLineId.getId());
        defaultTimeLine.setItems(new ArrayList<>());

        Map<String, TimeLine> timeLines = new HashMap<>();
        timeLines.put("default", defaultTimeLine);

        TimeLineModel timeLineModel = new TimeLineModel();
        timeLineModel.setId(timeLineModelCoordinate.getTimeLineModelId().getId());
        timeLineModel.setTimeLines(timeLines);

        storyDocService.addArtifact(timeLineModelCoordinate.getBlockCoordinate().getStoryDocId(), timeLineModelCoordinate.getBlockCoordinate().getBlockId(), ArtifactId.fromString(timeLineModelCoordinate.getTimeLineModelId().getId()),
                io.storydoc.server.timeline.domain.TimeLineModel.ARTIFACT_TYPE, name);
        save(timeLineModelCoordinate, timeLineModel);

    }

    @Override
    public void addItemToTimeLine(TimeLineCoordinate timeLineCoordinate, TimeLineItemId itemId, String name) {
        TimeLineModelCoordinate modelCoordinate = timeLineCoordinate.getTimeLineModelCoordinate();
        TimeLineModel timeLineModel = loadTimeLineModel(modelCoordinate);
        TimeLine timeLine = timeLineModel.getTimeLines().values().stream()
                .filter(e -> e.getId().equals(timeLineCoordinate.getTimeLineId().getId()))
                .findFirst()
                .get();
        TimeLineItem item = new TimeLineItem();
        item.setId(itemId.getId());
        item.setDescription(name);

        timeLine.getItems().add(item);
        save(modelCoordinate, timeLineModel);
    }

    private void save(TimeLineModelCoordinate timeLineModelCoordinate, TimeLineModel timeLineModel) {
        storyDocService.saveArtifact(ArtifactSaveContext.builder()
                .storyDocId(timeLineModelCoordinate.getBlockCoordinate().getStoryDocId())
                .blockId(timeLineModelCoordinate.getBlockCoordinate().getBlockId())
                .relativeUrn(getRelativeUrn(timeLineModelCoordinate))
                .serializer((OutputStream os) -> { write(timeLineModel, os);})
                .build());
    }

    private void write(TimeLineModel timeLineModel, OutputStream outputStream) throws IOException {
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(outputStream, timeLineModel);
    }

    private ResourceUrn getRelativeUrn(TimeLineModelCoordinate timeLineModelCoordinate) {
        return new ResourceUrn( new FolderURN(List.of()), timeLineModelCoordinate.getTimeLineModelId().getId() + ".json");
    }

    @Override
    public ResourceUrn getUrn(TimeLineModelCoordinate timeLineModelCoordinate) {
        return storyDocQueryService
                .getArtifactBlockFolder(timeLineModelCoordinate.getBlockCoordinate())
                .resolve(getRelativeUrn(timeLineModelCoordinate));
    }

    @Override
    public TimeLineModel loadTimeLineModel(TimeLineModelCoordinate timeLineModelCoordinate) {
        return storyDocService.loadArtifact(ArtifactLoadContext.builder()
                .blockCoordinate(timeLineModelCoordinate.getBlockCoordinate())
                .relativeUrn(getRelativeUrn(timeLineModelCoordinate))
                .deserializer(this::read)
                .build());
    }

    private TimeLineModel read(InputStream inputStream) throws IOException {
        return objectMapper.readValue(inputStream, TimeLineModel.class);
    }

}
