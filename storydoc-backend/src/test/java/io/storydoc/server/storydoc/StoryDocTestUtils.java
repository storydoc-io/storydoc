package io.storydoc.server.storydoc;

import io.storydoc.server.storydoc.app.StoryDocService;
import io.storydoc.server.storydoc.domain.BlockCoordinate;
import io.storydoc.server.storydoc.domain.BlockId;
import io.storydoc.server.storydoc.domain.StoryDocId;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class StoryDocTestUtils {

    private final StoryDocService storyDocService;

    public StoryDocTestUtils(StoryDocService storyDocService) {
        this.storyDocService = storyDocService;
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
        StoryDocId storyDocId = storyDocService.createDocument(story_name);
        return storyDocId;
    }

}
