package io.storydoc.server.storydoc.app;

import io.storydoc.server.storydoc.domain.*;
import io.storydoc.server.storydoc.domain.action.ArtifactLoadContext;
import io.storydoc.server.storydoc.domain.action.ArtifactSaveContext;
import io.storydoc.server.storydoc.domain.action.SaveBinaryArtifactContext;

import java.io.IOException;
import java.io.InputStream;

public interface StoryDocService {

    StoryDocId createDocument(String story_name);

    BlockId addArtifactBlock(StoryDocId storyDocId, String name);

    BlockId addArtifactBlock(StoryDocId storyDocId, SectionId sectionId);

    void removeBlock(StoryDocId storyDocId, BlockId blockId1);

    SectionId addSection(StoryDocId storyDocId);

    void addTag(StoryDocId storyDocId, BlockId blockId, String tag);

    void addArtifact(StoryDocId storyDocId, BlockId blockId, ArtifactId artifactId, String artifactType, String name);

    void saveArtifact(ArtifactSaveContext context);

    <A extends Artifact> A loadArtifact(ArtifactLoadContext artifactLoadContext);

    void saveBinaryArtifact(SaveBinaryArtifactContext context) throws IOException;

    ArtifactId createBinaryCollectionArtifact(ArtifactBlockCoordinate coordinate, String artifactType, String binaryType, String name);

    ItemId addItemToBinaryCollection(ArtifactBlockCoordinate coordinate, ArtifactId artifactId, String itemName, InputStream inputStream);

}
