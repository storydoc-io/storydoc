package io.storydoc.server.storydoc;

import io.storydoc.server.storydoc.app.StoryDocQueryService;
import io.storydoc.server.storydoc.app.StoryDocService;
import io.storydoc.server.storydoc.app.dto.StoryDocDTO;
import io.storydoc.server.storydoc.domain.*;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class StoryDocTestUtils {

    private final StoryDocService storyDocService;

    private final StoryDocQueryService storyDocQueryService;

    public StoryDocTestUtils(StoryDocService storyDocService, StoryDocQueryService storyDocQueryService) {
        this.storyDocService = storyDocService;
        this.storyDocQueryService = storyDocQueryService;
    }

    public BlockCoordinate create_storydoc_with_artifact_block() {
        StoryDocId storyDocId = create_storydoc();
        return add_artifact_block(storyDocId);
    }

    public BlockCoordinate add_artifact_block(StoryDocId storyDocId) {
        BlockId blockId = storyDocService.addArtifactBlock(storyDocId, "block_" + UUID.randomUUID());
        return BlockCoordinate.of(storyDocId, blockId);
    }

    public StoryDocId create_storydoc() {
        String story_name = "story_" + UUID.randomUUID();
        return storyDocService.createDocument(story_name);
    }

    public ArtifactCoordinate add_artifact(BlockCoordinate blockCoordinate) {
        ArtifactId artifactId = new ArtifactId("artifact-"+UUID.randomUUID());
        String name = "name-" + artifactId.getId();
        String type= "test-type";
        storyDocService.addArtifact(blockCoordinate, artifactId, type, name);
        return ArtifactCoordinate.of(blockCoordinate, artifactId);
    }

    public StoryDocDTO getStorydoc(StoryDocId storyDocId) {
        return storyDocQueryService.getDocument(storyDocId);
    }
}
