package io.storydoc.server.storydoc.app;

import io.storydoc.server.storydoc.domain.*;

import java.io.IOException;
import java.io.InputStream;

public interface StoryDocService {

    StoryDocId createDocument(String story_name);

    void removeDocument(StoryDocId storyDocId);

    BlockId addArtifactBlock(StoryDocId storyDocId, String name);

    BlockId addArtifactBlock(StoryDocId storyDocId, SectionId sectionId);

    void moveBlock(BlockCoordinate blockToMove, BlockCoordinate newParent, int childIndexInParent);

    void removeBlock(BlockCoordinate blockCoordinate);

    SectionId addSection(StoryDocId storyDocId);

    void addTag(StoryDocId storyDocId, BlockId blockId, String tag);

    void addArtifact(BlockCoordinate blockCoordinate, ArtifactId artifactId, String artifactType, String name);

    void saveArtifact(ArtifactCoordinate coordinate, ArtifactSerializer serializer);

    <A extends Artifact> A loadArtifact(ArtifactCoordinate coordinate, ArtifactDeserializer deserializer);

    void saveBinaryArtifact(ArtifactCoordinate artifactCoordinate, InputStream inputStream) throws IOException;

    void createBinaryCollectionArtifact(BlockCoordinate coordinate, ArtifactId artifactId, String artifactType, String binaryType, String name);

    ItemId addItemToBinaryCollection(BlockCoordinate coordinate, ArtifactId artifactId, String itemName, InputStream inputStream);

    void removeArtifact(ArtifactCoordinate artifactCoordinate);

    void renameDocument(StoryDocId storyDocId, String new_name);

    void renameBlock(BlockCoordinate blockCoordinate, String new_name);

    void renameArtifact(BlockCoordinate blockCoordinate, ArtifactId artifactId, String new_name);
}
