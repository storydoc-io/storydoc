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
public class CodeExecutionCoordinate {

    BlockCoordinate blockCoordinate;
    CodeExecutionId codeExecutionId;

    static public CodeExecutionCoordinate of(StoryDocId storyDocId, BlockId blockId, CodeExecutionId codeExecutionId) {
        return of(BlockCoordinate.of(storyDocId, blockId), codeExecutionId);
    }

    static public CodeExecutionCoordinate of(BlockCoordinate blockCoordinate, CodeExecutionId codeExecutionId) {
        return new CodeExecutionCoordinate(blockCoordinate, codeExecutionId);
    }

    public ArtifactCoordinate asArtifactCoordinate() {
        return ArtifactCoordinate.of(blockCoordinate, codeExecutionId.asArtifactId());
    }
}
