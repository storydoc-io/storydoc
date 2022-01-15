package io.storydoc.server.storydoc.domain.action;

import io.storydoc.server.storydoc.domain.Artifact;
import io.storydoc.server.storydoc.domain.ArtifactDeserializer;
import io.storydoc.server.storydoc.domain.BlockId;
import io.storydoc.server.storydoc.domain.StoryDocId;
import io.storydoc.server.workspace.domain.ResourceUrn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ArtifactLoadContext<A extends Artifact> {
    private StoryDocId storyDocId;
    private BlockId blockId;
    private ResourceUrn relativeUrn;
    private ArtifactDeserializer<A> deserializer;
}
