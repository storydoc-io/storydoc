package io.storydoc.server.storydoc;

import io.storydoc.server.storydoc.app.StoryDocService;
import io.storydoc.server.storydoc.domain.ArtifactBlockCoordinate;
import io.storydoc.server.storydoc.domain.BlockId;
import io.storydoc.server.storydoc.domain.StoryDocId;
import org.springframework.stereotype.Component;

@Component
public class StoryDocTestUtils {

    private final StoryDocService storyDocService;

    public StoryDocTestUtils(StoryDocService storyDocService) {
        this.storyDocService = storyDocService;
    }

    public ArtifactBlockCoordinate create_storydoc_with_artifact_block() {
        String story_name = "story_name";
        StoryDocId storyDocId = storyDocService.createDocument(story_name);

        BlockId blockId = storyDocService.addArtifactBlock(storyDocId, "block_name");
        return ArtifactBlockCoordinate.of(storyDocId, blockId);
    }

}
