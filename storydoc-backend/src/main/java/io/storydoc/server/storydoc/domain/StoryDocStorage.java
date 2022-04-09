package io.storydoc.server.storydoc.domain;

import io.storydoc.server.storydoc.infra.store.model.Association;
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

    // document management level

    ResourceUrn getStoryDocsUrn();

    StoryDocs loadDocuments();

    // document level

    void createDocument(StoryDocId storyDocId, String name);

    ResourceUrn getStoryDocUrn(StoryDocId storyDocId);

    StoryDoc loadDocument(StoryDocId storyDocId);

    void removeDocument(StoryDocId storyDocId);

    void renameDocument(StoryDocId id, String new_name);

    // section level

    void addSection(StoryDocId id, SectionId sectionId);

    // block level

    void addArtifactBlock(StoryDocId storyDocId, BlockId blockId, String name);

    void addArtifactBlock(StoryDocId id, BlockId blockId, SectionId sectionId);

    Block getBlock(BlockCoordinate coordinate);

    FolderURN getBlockFolder(StoryDocId storyDocId, BlockId blockId);

    void removeBlock(BlockCoordinate blockCoordinate);

    void renameBlock(BlockCoordinate blockCoordinate, String new_name);

    void moveBlock(BlockCoordinate blockToMove, BlockCoordinate newParent, int childIndexInParent);

    void addTag(StoryDocId id, BlockId blockId, String tag);

    // artifact level

    void createArtifact(BlockCoordinate blockCoordinate, ArtifactId artifactId, ArtifactMetaData metaData);

    void setArtifactContent(ArtifactCoordinate coordinate, ArtifactSerializer serializer);

    ArtifactMetaData getArtifactMetaData(BlockCoordinate coordinate, ArtifactId artifactId);

    ResourceUrn getArtifactUrn(ArtifactCoordinate artifactCoordinate);

    <A extends Artifact> A getArtifactContent(ArtifactCoordinate coordinate, ArtifactDeserializer deserializer);

    List<ArtifactId> findArtifacts(BlockCoordinate coordinate, Function<ArtifactMetaData, Boolean> filter);

    void removeArtifact(ArtifactCoordinate artifactCoordinate);

    void renameArtifact(BlockCoordinate blockCoordinate, ArtifactId artifactId, String new_name);

    void changeArtifactState(ArtifactCoordinate coordinate, ArtifactState state);

    public void addArtifactAssociation(ArtifactCoordinate coordinateFrom, ArtifactCoordinate coordinateTo, String name);

    // binary artifact

    void saveBinaryArtifact(ArtifactCoordinate artifactCoordinate, InputStream inputStream);

    InputStream getBinaryFromCollection(ArtifactCoordinate coordinate, ItemId itemId) throws WorkspaceException;

    // collection artifact

    void addItemToBinaryCollection(ArtifactCoordinate coordinate, String itemName, ItemId itemId, InputStream inputStream);

    void renameItemFromBinaryCollection(ArtifactCoordinate artifactCoordinate, ItemId itemId, String name);

    void removeItemFromBinaryCollection(ArtifactCoordinate artifactCoordinate, ItemId itemId);

    ResourceUrn getCollectionItemUrn(ArtifactCoordinate artifactCoordinate, ItemId itemId);

    List<Association> getAssociations(ArtifactCoordinate artifactCoordinate);
}
