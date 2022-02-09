package io.storydoc.server.storydoc.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlockCoordinate {

    private StoryDocId storyDocId;
    private BlockId blockId;

    public static BlockCoordinate of (StoryDocId storyDocId, BlockId blockId) {
        return new BlockCoordinate(storyDocId, blockId);
    }

}
