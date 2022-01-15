package io.storydoc.server.storydoc.domain;

import io.storydoc.server.storydoc.domain.action.ArtifactLoadContext;
import io.storydoc.server.storydoc.domain.action.ArtifactSaveContext;
import io.storydoc.server.storydoc.domain.action.SaveBinaryArtifactContext;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class StoryDoc {

    private StoryDocStorage storage;

    private StoryDocId id;

    public StoryDoc(StoryDocStorage storage) {
        this.storage = storage;
    }

    public BlockId addBlock(BlockId blockId) {
        storage.addArtifactBlock(id, blockId);
        return blockId;
    }


    public void removeBlock(BlockId blockId) {
        storage.removeBlock(id, blockId);
    }

    public SectionId addSection(SectionId sectionId) {
        storage.addSection(id, sectionId);
        return sectionId;

    }

    public BlockId addBlock(BlockId blockId, SectionId sectionId) {
        storage.addArtifactBlock(id, blockId, sectionId);
        return blockId;
    }

    public void addTag(BlockId blockId, String tag) {
        storage.addTag(id, blockId, tag);
    }

    public void saveArtifact(ArtifactSaveContext context) {
        storage.saveArtifact(context);
    }

    public <A extends Artifact> A loadArtifact(ArtifactLoadContext context) {
        return storage.loadArtifact(context);
    }

    public void addArtifact(BlockId blockId, ArtifactId artifactId, ArtifactMetaData metaData) {
        storage.addArtifact(id, blockId, artifactId, metaData);
    }

    public void saveBinaryArtifact(SaveBinaryArtifactContext context) {
        storage.saveBinaryArtifact(context);
    }
}
