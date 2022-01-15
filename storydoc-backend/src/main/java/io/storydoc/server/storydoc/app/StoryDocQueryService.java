package io.storydoc.server.storydoc.app;

import io.storydoc.server.storydoc.app.dto.StoryDocDTO;
import io.storydoc.server.storydoc.domain.*;
import io.storydoc.server.workspace.domain.FolderURN;

import java.util.List;

public interface StoryDocQueryService {

    StoryDocDTO getDocument(StoryDocId storyDocId);
    FolderURN getArtifactBlockFolder(StoryDocId storyDocId, BlockId blockId);
    List<ArtifactId> getArtifactsByType(ArtifactBlockCoordinate coordinate, String artifactType);
    ArtifactMetaData getArtifactMetaData(ArtifactBlockCoordinate coordinate, ArtifactId artifactId);
}
