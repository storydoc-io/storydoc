package io.storydoc.server.document.app;

import io.storydoc.server.document.domain.BlockId;
import io.storydoc.server.document.domain.SectionId;
import io.storydoc.server.document.domain.StoryDocId;

public interface DocumentService {

    StoryDocId createDocument();

    BlockId addArtifactBlock(StoryDocId storyDocId);

    BlockId addArtifactBlock(StoryDocId storyDocId, SectionId sectionId);

    void removeBlock(StoryDocId storyDocId, BlockId blockId1);

    SectionId addSection(StoryDocId storyDocId);

    void addTag(StoryDocId storyDocId, BlockId blockId, String tag);
}
