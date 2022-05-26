package io.storydoc.server.storydoc;

import io.storydoc.server.infra.StitchFactory;
import io.storydoc.server.storydoc.app.StoryDocQueryService;
import io.storydoc.server.storydoc.app.StoryDocService;
import io.storydoc.server.storydoc.app.dto.StoryDocDTO;
import io.storydoc.server.storydoc.domain.*;
import io.storydoc.stitch.ScenarioTracer;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class StoryDocTestUtils {

    ScenarioTracer scenarioTracer = StitchFactory.getScenarioTracer();

    private void given(String text) {
        scenarioTracer.bdd("given", text);
    }

    private final StoryDocService storyDocService;

    private final StoryDocQueryService storyDocQueryService;

    public StoryDocTestUtils(StoryDocService storyDocService, StoryDocQueryService storyDocQueryService) {
        this.storyDocService = storyDocService;
        this.storyDocQueryService = storyDocQueryService;
    }

    public BlockCoordinate create_storydoc_with_artifact_block() {
        given("given a storydoc with an artifact block");
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

    public ArtifactCoordinate add_artifact(BlockCoordinate blockCoordinate, String type) {
        ArtifactId artifactId = new ArtifactId("artifact-"+UUID.randomUUID());
        String name = "name-" + artifactId.getId();
        storyDocService.addArtifact(blockCoordinate, artifactId, type, name);
        return ArtifactCoordinate.of(blockCoordinate, artifactId);

    }

    public ArtifactCoordinate add_artifact(BlockCoordinate blockCoordinate) {
        return add_artifact(blockCoordinate, "test-type");
    }

    public StoryDocDTO getStorydoc(StoryDocId storyDocId) {
        return storyDocQueryService.getDocument(storyDocId);
    }
}
