package io.storydoc.server.storydoc.domain;

import io.storydoc.server.storydoc.domain.action.*;
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

    public BlockId addBlock(BlockId blockId, String name) {
        storage.addArtifactBlock(id, blockId, name);
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

    public void createBinaryCollectionArtifact(CreateBinaryCollectionArtifactAction action) {
        storage.createBinaryCollectionArtifact(action);
    }

    public void addItemToBinaryCollection(AddToBinaryCollectionAction action) {
        storage.addItemToBinaryCollection(action);
    }

    public void rename(String new_name) {
        storage.renameDocument(id, new_name);
    }

    public void renameBlock(ArtifactBlockCoordinate blockCoordinate, String new_name) {
        storage.renameBlock(blockCoordinate, new_name);
    }
}
