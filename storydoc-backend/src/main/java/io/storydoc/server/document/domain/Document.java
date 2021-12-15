package io.storydoc.server.document.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class Document {

    private DocumentStore documentStore;

    private StoryDocId id;

    public Document(DocumentStore documentStore) {
        this.documentStore = documentStore;
    }

    public BlockId addBlock(BlockId blockId) {
        documentStore.addArtifactBlock(id, blockId);
        return blockId;
    }


    public void removeBlock(BlockId blockId) {
        documentStore.removeBlock(id, blockId);
    }

    public SectionId addSection(SectionId sectionId) {
        documentStore.addSection(id, sectionId);
        return sectionId;

    }

    public BlockId addBlock(BlockId blockId, SectionId sectionId) {
        documentStore.addArtifactBlock(id, blockId, sectionId);
        return blockId;
    }

    public void addTag(BlockId blockId, String tag) {
        documentStore.addTag(id, blockId, tag);
    }

}
