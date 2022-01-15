package io.storydoc.server.storydoc.domain.action;

import io.storydoc.server.storydoc.domain.ArtifactSerializer;
import io.storydoc.server.storydoc.domain.BlockId;
import io.storydoc.server.storydoc.domain.StoryDocId;
import io.storydoc.server.workspace.domain.ResourceUrn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ArtifactSaveContext {
    private StoryDocId storyDocId;
    private BlockId blockId;
    private ResourceUrn relativeUrn;
    ArtifactSerializer serializer;
}
