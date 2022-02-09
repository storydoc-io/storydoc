package io.storydoc.server.storydoc.app;

import io.storydoc.server.storydoc.app.dto.ArtifactDTO;
import io.storydoc.server.storydoc.app.dto.StoryDocDTO;
import io.storydoc.server.storydoc.app.dto.StoryDocSummaryDTO;
import io.storydoc.server.storydoc.domain.*;
import io.storydoc.server.workspace.domain.FolderURN;
import io.storydoc.server.workspace.domain.WorkspaceException;

import java.io.InputStream;
import java.util.List;

public interface StoryDocQueryService {
    List<StoryDocSummaryDTO> getStoryDocs();
    StoryDocSummaryDTO getStoryDocSummary(StoryDocId storyDocId);
    StoryDocDTO getDocument(StoryDocId storyDocId);
    FolderURN getArtifactBlockFolder(BlockCoordinate blockCoordinate);
    List<ArtifactId> getArtifactsByType(BlockCoordinate coordinate, String artifactType);
    ArtifactMetaData getArtifactMetaData(BlockCoordinate coordinate, ArtifactId artifactId);
    ArtifactDTO getArtifact(BlockCoordinate coordinate, ArtifactId asArtifactId);
    InputStream getBinaryFromCollection(BlockCoordinate coordinate, ArtifactId asArtifactId, ItemId asItemId) throws WorkspaceException;

}
