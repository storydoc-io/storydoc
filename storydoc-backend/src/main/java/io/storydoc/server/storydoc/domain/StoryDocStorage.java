package io.storydoc.server.storydoc.domain;

import io.storydoc.server.storydoc.domain.action.*;
import io.storydoc.server.storydoc.infra.store.model.Block;
import io.storydoc.server.storydoc.infra.store.model.StoryDoc;
import io.storydoc.server.storydoc.infra.store.model.StoryDocs;
import io.storydoc.server.workspace.domain.FolderURN;
import io.storydoc.server.workspace.domain.ResourceUrn;
import io.storydoc.server.workspace.domain.WorkspaceException;

import java.io.InputStream;
import java.util.List;
import java.util.function.Function;

public interface StoryDocStorage {
    ResourceUrn getStoryDocUrn(StoryDocId storyDocId);
    ResourceUrn getStoryDocsUrn();

    StoryDoc loadDocument(StoryDocId storyDocId);
    void createDocument(StoryDocId storyDocId, String name);

    void addArtifactBlock(StoryDocId storyDocId, BlockId blockId, String name);
    void addArtifactBlock(StoryDocId id, BlockId blockId, SectionId sectionId);
    void removeBlock(StoryDocId id, BlockId blockId);
    Block getBlock(BlockCoordinate coordinate);

    void addSection(StoryDocId id, SectionId sectionId);

    void addTag(StoryDocId id, BlockId blockId, String tag);

    void saveArtifact(ArtifactSaveContext context);

    <A extends Artifact> A loadArtifact(ArtifactLoadContext context);

    void addArtifact(StoryDocId id, BlockId blockId, ArtifactId artifactId, ArtifactMetaData metaData);

    FolderURN getArtifactBlockFolder(StoryDocId storyDocId, BlockId blockId);

    List<ArtifactId> getArtifacts(BlockCoordinate coordinate, Function<ArtifactMetaData, Boolean> filter);

    void saveBinaryArtifact(SaveBinaryArtifactContext context);

    ArtifactMetaData getArtifactMetaData(BlockCoordinate coordinate, ArtifactId artifactId);

    StoryDocs loadDocuments();

    void createBinaryCollectionArtifact(CreateBinaryCollectionArtifactAction action);

    void addItemToBinaryCollection(AddToBinaryCollectionAction action);

    InputStream getBinaryFromCollection(BlockCoordinate coordinate, ArtifactId artifactId, ItemId itemId) throws WorkspaceException;

    void removeDocument(StoryDocId storyDocId);

    void renameDocument(StoryDocId id, String new_name);

    void renameBlock(BlockCoordinate blockCoordinate, String new_name);

    void renameArtifact(BlockCoordinate blockCoordinate, ArtifactId artifactId, String new_name);

    void moveBlock(BlockCoordinate blockToMove, BlockCoordinate newParent, int childIndexInParent);
}
