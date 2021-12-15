package io.storydoc.server.document.domain;

import io.storydoc.server.document.infra.store.model.StoryDoc;

public interface DocumentStore {
    StoryDoc loadDocument(StoryDocId storyDocId);
    void createDocument(StoryDocId storyDocId);

    void addArtifactBlock(StoryDocId storyDocId, BlockId blockId);
    void addArtifactBlock(StoryDocId id, BlockId blockId, SectionId sectionId);
    void removeBlock(StoryDocId id, BlockId blockId);

    String getContentAsString(StoryDocId storyDocId);

    void addSection(StoryDocId id, SectionId sectionId);

    void addTag(StoryDocId id, BlockId blockId, String tag);
}
