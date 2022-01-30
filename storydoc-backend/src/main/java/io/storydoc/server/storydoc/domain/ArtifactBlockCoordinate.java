package io.storydoc.server.storydoc.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArtifactBlockCoordinate {

    private StoryDocId storyDocId;
    private BlockId blockId;

    public static ArtifactBlockCoordinate of (StoryDocId storyDocId, BlockId blockId) {
        return new ArtifactBlockCoordinate(storyDocId, blockId);
    }

}
