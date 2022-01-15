package io.storydoc.server.storydoc.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ArtifactBlockCoordinate {

    private StoryDocId storyDocId;
    private SectionId sectionId;
    private BlockId blockId;

}
