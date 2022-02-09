package io.storydoc.server.ui.infra.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.storydoc.server.storydoc.app.StoryDocQueryService;
import io.storydoc.server.storydoc.app.StoryDocService;
import io.storydoc.server.storydoc.domain.BlockCoordinate;
import io.storydoc.server.storydoc.domain.ArtifactId;
import io.storydoc.server.storydoc.domain.ItemId;
import io.storydoc.server.storydoc.domain.action.ArtifactLoadContext;
import io.storydoc.server.storydoc.domain.action.ArtifactSaveContext;
import io.storydoc.server.timeline.domain.TimeLineCoordinate;
import io.storydoc.server.timeline.domain.TimeLineItemId;
import io.storydoc.server.ui.domain.*;
import io.storydoc.server.ui.domain.action.CreateScreenShotCollectionArtifactAction;
import io.storydoc.server.ui.domain.action.UploadScreenShotToCollectionAction;
import io.storydoc.server.workspace.domain.FolderURN;
import io.storydoc.server.workspace.domain.ResourceUrn;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class UIStorageImpl implements UIStorage {

    private ObjectMapper objectMapper;

    private final StoryDocService storyDocService;

    private final StoryDocQueryService storyDocQueryService;

    public UIStorageImpl(StoryDocService storyDocService, StoryDocQueryService storyDocQueryService) {
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
    public void createUIScenario(UIScenarioCoordinate scenarioCoordinate, String name) {
        UIScenario UIScenario = new UIScenario();
        UIScenario.setId(scenarioCoordinate.getUiScenarioId().getId());
        UIScenario.setScreenshots(new ArrayList<>());

        storyDocService.addArtifact(scenarioCoordinate.getBlockCoordinate().getStoryDocId(), scenarioCoordinate.getBlockCoordinate().getBlockId(), ArtifactId.fromString(scenarioCoordinate.getUiScenarioId().getId()),
                io.storydoc.server.ui.domain.UIScenario.ARTIFACT_TYPE, name);
        save(scenarioCoordinate, UIScenario);
    }

    @Override
    public void createScreenshotCollection(CreateScreenShotCollectionArtifactAction action) {
        ArtifactId artifactId = storyDocService.createBinaryCollectionArtifact(action.getCoordinate(), ScreenShotCollection.ARTIFACT_TYPE, "image", action.getName());
        action.setCollectionId(ScreenShotCollectionId.fromString(artifactId.getId()));
    }

    @Override
    public void uploadScreenShot(UploadScreenShotToCollectionAction action) {
        ItemId itemId = storyDocService.addItemToBinaryCollection(action.getCollectionCoordinate().getBlockCoordinate(), action.getCollectionCoordinate().getScreenShotCollectionId().asArtifactId(), action.getName(), action.getInputStream());
        action.setScreenshotId(ScreenShotId.fromString(itemId.getId()));
    }

    @Override
    public void addScreenshot(UIScenarioCoordinate scenarioCoordinate, ScreenshotCoordinate screenshotCoordinate, TimeLineItemId timeLineItemId) {
        UIScenario uiScenario = loadUIScenario(scenarioCoordinate);
        uiScenario.getScreenshots().add(new ScreenshotTimeLineItem(screenshotCoordinate, timeLineItemId.getId()));
        save(scenarioCoordinate, uiScenario);
    }

    @Override
    public void setTimeLine(UIScenarioCoordinate scenarioCoordinate, TimeLineCoordinate timeLineCoordinate) {
        UIScenario uiScenario = loadUIScenario(scenarioCoordinate);
        uiScenario.setTimeLineModelCoordinate(timeLineCoordinate.getTimeLineModelCoordinate());
        uiScenario.setTimeLineId(timeLineCoordinate.getTimeLineId());
        save(scenarioCoordinate, uiScenario);
    }

    private void save(UIScenarioCoordinate scenarioCoordinate, UIScenario UIScenario) {
        storyDocService.saveArtifact(ArtifactSaveContext.builder()
            .storyDocId(scenarioCoordinate.getBlockCoordinate().getStoryDocId())
            .blockId(scenarioCoordinate.getBlockCoordinate().getBlockId())
            .relativeUrn(getRelativeUIArtifactResourceUrn(UIScenarioId.fromString(UIScenario.getId())))
            .serializer((OutputStream os) -> { write(UIScenario, os);})
            .build());
    }

    private void write(UIScenario UIScenario, OutputStream outputStream) throws IOException {
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(outputStream, UIScenario);
    }

    public ResourceUrn getUIScenarioUrn(UIScenarioCoordinate scenarioCoordinate) {
        return storyDocQueryService.getArtifactBlockFolder(scenarioCoordinate.getBlockCoordinate()).resolve(getRelativeUIArtifactResourceUrn(scenarioCoordinate.getUiScenarioId()));
    }

    @Override
    public ResourceUrn getScreenshotUrn(BlockCoordinate blockCoordinate, ScreenShotId screenshotId) {
        return storyDocQueryService.getArtifactBlockFolder(blockCoordinate).resolve(getRelativeScreenshotArtifactResourceUrn(screenshotId));
    }



    private ResourceUrn getRelativeUIArtifactResourceUrn(UIScenarioId UIScenarioId) {
        return new ResourceUrn( new FolderURN(List.of()), UIScenarioId.getId() + ".json");
    }

    private ResourceUrn getRelativeScreenshotArtifactResourceUrn(ScreenShotId screenshotId) {
        return new ResourceUrn( new FolderURN(List.of()), screenshotId.getId());
    }

    @Override
    public UIScenario loadUIScenario(UIScenarioCoordinate scenarioCoordinate) {
        return storyDocService.loadArtifact(ArtifactLoadContext.builder()
                .relativeUrn(getRelativeUIArtifactResourceUrn(scenarioCoordinate.getUiScenarioId()))
                .blockCoordinate(scenarioCoordinate.getBlockCoordinate())
                .deserializer(this::read)
                .build());
    }

    private UIScenario read(InputStream inputStream) throws IOException {
        return objectMapper.readValue(inputStream, UIScenario.class);
    }

}
