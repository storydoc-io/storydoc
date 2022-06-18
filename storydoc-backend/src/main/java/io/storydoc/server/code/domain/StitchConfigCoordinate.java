package io.storydoc.server.code.domain;

import io.storydoc.server.storydoc.domain.ArtifactCoordinate;
import io.storydoc.server.storydoc.domain.BlockCoordinate;
import io.storydoc.server.storydoc.domain.BlockId;
import io.storydoc.server.storydoc.domain.StoryDocId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StitchConfigCoordinate {

    BlockCoordinate blockCoordinate;
    StitchConfigId stitchConfigId;

    static public StitchConfigCoordinate of(StoryDocId storyDocId, BlockId blockId, StitchConfigId stitchConfigId) {
        return of(BlockCoordinate.of(storyDocId, blockId), stitchConfigId);
    }

    static public StitchConfigCoordinate of(BlockCoordinate blockCoordinate, StitchConfigId stitchConfigId) {
        return new StitchConfigCoordinate(blockCoordinate, stitchConfigId);
    }

    public static StitchConfigCoordinate of(ArtifactCoordinate artifactCoordinate) {
        return of(artifactCoordinate.getBlockCoordinate(), StitchConfigId.fromString(artifactCoordinate.getArtifactId().getId()));
    }

    public ArtifactCoordinate asArtifactCoordinate() {
        return ArtifactCoordinate.of(blockCoordinate, stitchConfigId.asArtifactId());
    }
}
