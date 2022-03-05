package io.storydoc.server.ui.infra.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.storydoc.server.storydoc.app.StoryDocQueryService;
import io.storydoc.server.storydoc.app.StoryDocService;
import io.storydoc.server.storydoc.domain.ArtifactState;
import io.storydoc.server.storydoc.domain.BlockCoordinate;
import io.storydoc.server.storydoc.domain.ItemId;
import io.storydoc.server.timeline.domain.TimeLineItemId;
import io.storydoc.server.timeline.domain.TimeLineModelCoordinate;
import io.storydoc.server.ui.domain.*;
import io.storydoc.server.workspace.domain.FolderURN;
import io.storydoc.server.workspace.domain.ResourceUrn;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        storyDocService.addArtifact(scenarioCoordinate.getBlockCoordinate(), scenarioCoordinate.getUiScenarioId().asArtifactId(),
                io.storydoc.server.ui.domain.UIScenario.ARTIFACT_TYPE, name) ;
        save(scenarioCoordinate, UIScenario);
    }

    @Override
    public void createScreenshotCollection(BlockCoordinate coordinate, ScreenShotCollectionId screenShotCollectionId, String name) {
        storyDocService.createBinaryCollectionArtifact(coordinate, screenShotCollectionId.asArtifactId(), ScreenShotCollection.ARTIFACT_TYPE, "image", name);
    }

    @Override
    public ScreenShotId uploadScreenShot(ScreenshotCollectionCoordinate collectionCoordinate, InputStream inputStream, String name) {
        ItemId itemId = storyDocService.addItemToBinaryCollection(collectionCoordinate.getBlockCoordinate(), collectionCoordinate.getScreenShotCollectionId().asArtifactId(), name, inputStream);
        return ScreenShotId.fromString(itemId.getId());
    }

    @Override
    public void setTimeLineModel(UIScenarioCoordinate scenarioCoordinate, TimeLineModelCoordinate timeLineModelCoordinate) {
        UIScenario uiScenario = loadUIScenario(scenarioCoordinate);
        uiScenario.setTimeLineModelCoordinate(timeLineModelCoordinate);
        save(scenarioCoordinate, uiScenario);
        storyDocService.changeArtifactState(scenarioCoordinate.asArtifactCoordinate(), ArtifactState.READY);
    }

    @Override
    public void addScreenshot(UIScenarioCoordinate scenarioCoordinate, ScreenshotCoordinate screenshotCoordinate, TimeLineItemId timeLineItemId) {
        UIScenario uiScenario = loadUIScenario(scenarioCoordinate);
        getScreenshotTimeLineItem(uiScenario, timeLineItemId)
            .ifPresent(screenshotTimeLineItem -> uiScenario.getScreenshots().remove(screenshotTimeLineItem));
        uiScenario.getScreenshots().add(new ScreenshotTimeLineItem(screenshotCoordinate, timeLineItemId.getId()));
        save(scenarioCoordinate, uiScenario);
    }

    @Override
    public void removeScreenshot(UIScenarioCoordinate scenarioCoordinate, TimeLineItemId timeLineItemId) {
        UIScenario uiScenario = loadUIScenario(scenarioCoordinate);
        Optional<ScreenshotTimeLineItem> toRemove = getScreenshotTimeLineItem(uiScenario, timeLineItemId);
        if (toRemove.isPresent()) {
            uiScenario.getScreenshots().remove(toRemove.get());
            save(scenarioCoordinate, uiScenario);
        }
    }

    private Optional<ScreenshotTimeLineItem> getScreenshotTimeLineItem(UIScenario uiScenario, TimeLineItemId timeLineItemId) {
        return uiScenario.getScreenshots().stream()
                .filter(i -> i.getTimeLineItemId().equals(timeLineItemId.getId()))
                .findFirst();
    }

    private void save(UIScenarioCoordinate scenarioCoordinate, UIScenario UIScenario) {
        storyDocService.saveArtifact(scenarioCoordinate.asArtifactCoordinate(), (OutputStream os) -> { write(UIScenario, os);});
    }

    private void write(UIScenario UIScenario, OutputStream outputStream) throws IOException {
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(outputStream, UIScenario);
    }

    public ResourceUrn getUIScenarioUrn(UIScenarioCoordinate scenarioCoordinate) {
        return storyDocQueryService.getArtifactBlockFolder(scenarioCoordinate.getBlockCoordinate()).resolve(getRelativeUIArtifactResourceUrn(scenarioCoordinate.getUiScenarioId()));
    }

    @Override
    public ResourceUrn getScreenshotUrn(ScreenshotCollectionCoordinate collectionCoordinate, ScreenShotId screenshotId) {
        return storyDocQueryService.getArtifactItemUrn(collectionCoordinate.asArtifactCoordinate(), screenshotId.asItemId());
    }

    private ResourceUrn getRelativeUIArtifactResourceUrn(UIScenarioId UIScenarioId) {
        return new ResourceUrn( new FolderURN(List.of()), UIScenarioId.getId() + ".json");
    }

    private FolderURN getRelativeScreenshotCollectionFolderUrn(ScreenShotCollectionId screenShotCollectionId) {
        return new FolderURN(List.of(screenShotCollectionId.getId()));
    }


    private ResourceUrn getRelativeScreenshotCollectionUrn(ScreenShotCollectionId screenShotCollectionId) {
        return new ResourceUrn( new FolderURN(List.of()), screenShotCollectionId.getId());
    }

    private ResourceUrn getRelativeScreenshotUrn(ScreenShotId screenShotId) {
        return new ResourceUrn( new FolderURN(List.of()), screenShotId.getId());
    }

    @Override
    public UIScenario loadUIScenario(UIScenarioCoordinate scenarioCoordinate) {
        return storyDocService.loadArtifact(scenarioCoordinate.asArtifactCoordinate(), this::read);
    }

    private UIScenario read(InputStream inputStream) throws IOException {
        return objectMapper.readValue(inputStream, UIScenario.class);
    }

}
