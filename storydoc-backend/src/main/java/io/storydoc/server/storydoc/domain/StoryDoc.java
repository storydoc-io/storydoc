package io.storydoc.server.storydoc.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.InputStream;

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


    public void removeBlock(BlockCoordinate blockCoordinate) {
        storage.removeBlock(blockCoordinate);
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

    public void saveArtifact(ArtifactCoordinate coordinate, ArtifactSerializer serializer) {
        storage.setArtifactContent(coordinate, serializer);
    }

    public <A extends Artifact> A loadArtifact(ArtifactCoordinate coordinate, ArtifactDeserializer deserializer) {
        return storage.getArtifactContent(coordinate, deserializer);
    }

    public void addArtifact(BlockCoordinate blockCoordinate, ArtifactId artifactId, ArtifactMetaData metaData) {
        storage.createArtifact(blockCoordinate, artifactId, metaData);
    }

    public void saveBinaryArtifact(ArtifactCoordinate artifactCoordinate, InputStream inputStream) {
        storage.saveBinaryArtifact(artifactCoordinate, inputStream);
    }

    public void addItemToBinaryCollection(ArtifactCoordinate coordinate, String itemName, ItemId itemId, InputStream inputStream) {
        storage.addItemToBinaryCollection(coordinate, itemName, itemId, inputStream);
    }

    public void rename(String new_name) {
        storage.renameDocument(id, new_name);
    }

    public void renameBlock(BlockCoordinate blockCoordinate, String new_name) {
        storage.renameBlock(blockCoordinate, new_name);
    }

    public void renameArtifact(BlockCoordinate blockCoordinate, ArtifactId artifactId, String new_name) {
        storage.renameArtifact(blockCoordinate, artifactId, new_name);
    }

    public void moveBlock(BlockCoordinate blockToMove, BlockCoordinate newParent, int childIndexInParent) {
        storage.moveBlock(blockToMove, newParent, childIndexInParent);
    }

    public void deleteArtifact(ArtifactCoordinate artifactCoordinate) {
        storage.removeArtifact(artifactCoordinate);
    }
}
