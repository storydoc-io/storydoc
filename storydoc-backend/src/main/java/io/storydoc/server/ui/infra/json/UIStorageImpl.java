package io.storydoc.server.ui.infra.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.storydoc.server.storydoc.app.StoryDocQueryService;
import io.storydoc.server.storydoc.app.StoryDocService;
import io.storydoc.server.storydoc.domain.ArtifactBlockCoordinate;
import io.storydoc.server.storydoc.domain.ArtifactId;
import io.storydoc.server.storydoc.domain.ItemId;
import io.storydoc.server.storydoc.domain.action.ArtifactLoadContext;
import io.storydoc.server.storydoc.domain.action.ArtifactSaveContext;
import io.storydoc.server.storydoc.domain.action.SaveBinaryArtifactContext;
import io.storydoc.server.ui.domain.*;
import io.storydoc.server.ui.domain.action.CreateScreenShotCollectionArtifactAction;
import io.storydoc.server.ui.domain.action.UploadScreenShotToCollectionAction;
import io.storydoc.server.workspace.domain.FolderURN;
import io.storydoc.server.workspace.domain.ResourceUrn;
import lombok.SneakyThrows;
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
    public void createMockUI(ArtifactBlockCoordinate coordinate, MockUIId uiId, String name) {
        MockUI mockUI = new MockUI();
        mockUI.setId(uiId.getId());
        mockUI.setScreenshots(new ArrayList<>());

        storyDocService.addArtifact(coordinate.getStoryDocId(), coordinate.getBlockId(), ArtifactId.fromString(uiId.getId()),
                io.storydoc.server.ui.domain.MockUI.ARTIFACT_TYPE, name);
        save(coordinate, mockUI);
    }

    @Override
    public void createScreenshotCollection(CreateScreenShotCollectionArtifactAction action) {
        ArtifactId artifactId = storyDocService.createBinaryCollectionArtifact(action.getCoordinate(), ScreenShotCollection.ARTIFACT_TYPE, "image", action.getName());
        action.setCollectionId(ScreenShotCollectionId.fromString(artifactId.getId()));
    }

    @Override
    public void uploadScreenShot(UploadScreenShotToCollectionAction action) {
        ItemId itemId = storyDocService.addItemToBinaryCollection(action.getCoordinate(), action.getCollectionId().asArtifactId(), action.getName(), action.getInputStream());
        action.setScreenshotId(ScreenShotId.fromString(itemId.getId()));
    }

    @Override
    @SneakyThrows
    public void createScreenshot(ArtifactBlockCoordinate coordinate, ScreenShotId screenshotId, InputStream inputStream, String name) {
        storyDocService.addArtifact(coordinate.getStoryDocId(), coordinate.getBlockId(), screenshotId.asArtifactId(),
                io.storydoc.server.ui.domain.Screenshot.ARTIFACT_TYPE, name);
        storyDocService.saveBinaryArtifact(SaveBinaryArtifactContext.builder()
                .coordinate(coordinate)
                .artifactId(screenshotId.asArtifactId())
                .inputStream(inputStream)
                .build());

    }

    @Override
    public void addScreenshot(ArtifactBlockCoordinate coordinate, MockUIId mockUIId, ScreenShotId screenshotId) {
        MockUI mockUI = loadMockUI(coordinate, mockUIId);
        mockUI.getScreenshots().add(new io.storydoc.server.ui.infra.json.Screenshot(screenshotId.getId()));
        save(coordinate, mockUI);
    }

    private void save(ArtifactBlockCoordinate coordinate, MockUI mockUI) {
        storyDocService.saveArtifact(ArtifactSaveContext.builder()
            .storyDocId(coordinate.getStoryDocId())
            .blockId(coordinate.getBlockId())
            .relativeUrn(getRelativeUIArtifactResourceUrn(MockUIId.fromString(mockUI.getId())))
            .serializer((OutputStream os) -> { write(mockUI, os);})
            .build());
    }

    private void write(MockUI mockUI,  OutputStream outputStream) throws IOException {
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(outputStream, mockUI);
    }

    public ResourceUrn getUIArtifactResourceUrn(ArtifactBlockCoordinate coordinate, MockUIId mockUIId) {
        return storyDocQueryService.getArtifactBlockFolder(coordinate.getStoryDocId(), coordinate.getBlockId()).resolve(getRelativeUIArtifactResourceUrn(mockUIId));
    }

    @Override
    public ResourceUrn getScreenshotArtifactResourceUrn(ArtifactBlockCoordinate coordinate, ScreenShotId screenshotId) {
        return storyDocQueryService.getArtifactBlockFolder(coordinate.getStoryDocId(), coordinate.getBlockId()).resolve(getRelativeScreenshotArtifactResourceUrn(screenshotId));
    }



    private ResourceUrn getRelativeUIArtifactResourceUrn(MockUIId mockUIId) {
        return new ResourceUrn( new FolderURN(List.of()), mockUIId.getId() + ".json");
    }

    private ResourceUrn getRelativeScreenshotArtifactResourceUrn(ScreenShotId screenshotId) {
        return new ResourceUrn( new FolderURN(List.of()), screenshotId.getId());
    }

    @Override
    public MockUI loadMockUI(ArtifactBlockCoordinate coordinate, MockUIId mockUIId) {
        return storyDocService.loadArtifact(ArtifactLoadContext.builder()
                .relativeUrn(getRelativeUIArtifactResourceUrn(mockUIId))
                .storyDocId(coordinate.getStoryDocId())
                .blockId(coordinate.getBlockId())
                .deserializer(this::read)
                .build());
    }

    private MockUI read(InputStream inputStream) throws IOException {
        return objectMapper.readValue(inputStream, MockUI.class);
    }

}
