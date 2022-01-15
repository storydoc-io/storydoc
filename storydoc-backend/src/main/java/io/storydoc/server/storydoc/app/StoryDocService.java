package io.storydoc.server.storydoc.app;

import io.storydoc.server.storydoc.domain.*;
import io.storydoc.server.storydoc.domain.action.ArtifactLoadContext;
import io.storydoc.server.storydoc.domain.action.ArtifactSaveContext;
import io.storydoc.server.storydoc.domain.action.SaveBinaryArtifactContext;

import java.io.IOException;

public interface StoryDocService {

    StoryDocId createDocument();

    BlockId addArtifactBlock(StoryDocId storyDocId);

    BlockId addArtifactBlock(StoryDocId storyDocId, SectionId sectionId);

    void removeBlock(StoryDocId storyDocId, BlockId blockId1);

    SectionId addSection(StoryDocId storyDocId);

    void addTag(StoryDocId storyDocId, BlockId blockId, String tag);

    void addArtifact(StoryDocId storyDocId, BlockId blockId, ArtifactId artifactId, String artifactType, String name);

    void saveArtifact(ArtifactSaveContext context);

    <A extends Artifact> A loadArtifact(ArtifactLoadContext artifactLoadContext);

    void saveBinaryArtifact(SaveBinaryArtifactContext context) throws IOException;
}
