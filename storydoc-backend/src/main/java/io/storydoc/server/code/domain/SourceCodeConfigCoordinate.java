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
public class SourceCodeConfigCoordinate {

    BlockCoordinate blockCoordinate;
    SourceCodeConfigId sourceCodeConfigId;

    static public SourceCodeConfigCoordinate of(StoryDocId storyDocId, BlockId blockId, SourceCodeConfigId sourceCodeConfigId) {
        return of(BlockCoordinate.of(storyDocId, blockId), sourceCodeConfigId);
    }

    static public SourceCodeConfigCoordinate of(BlockCoordinate blockCoordinate, SourceCodeConfigId sourceCodeConfigId) {
        return new SourceCodeConfigCoordinate(blockCoordinate, sourceCodeConfigId);
    }

    public static SourceCodeConfigCoordinate of(ArtifactCoordinate artifactCoordinate) {
        return of(artifactCoordinate.getBlockCoordinate(), SourceCodeConfigId.fromString(artifactCoordinate.getArtifactId().getId()));
    }

    public ArtifactCoordinate asArtifactCoordinate() {
        return ArtifactCoordinate.of(blockCoordinate, sourceCodeConfigId.asArtifactId());
    }
}
