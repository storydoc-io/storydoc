package io.storydoc.server.storydoc.app;

import io.storydoc.server.infra.IDGenerator;
import io.storydoc.server.storydoc.domain.*;
import io.storydoc.server.storydoc.domain.action.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStream;


@Service
public class StoryDocServiceImpl implements StoryDocService {

    private final StoryDocDomainService domainService;

    private final IDGenerator idGenerator;

    public StoryDocServiceImpl(StoryDocDomainService domainService, IDGenerator idGenerator) {
        this.domainService = domainService;
        this.idGenerator = idGenerator;
    }

    @Override
    public StoryDocId createDocument(String name) {
        StoryDocId storyDocId = new StoryDocId(idGenerator.generateID("STORY"));
        return domainService.createDocument(storyDocId, name);
    }

    @Override
    public void removeDocument(StoryDocId storyDocId) {
        domainService.removeDocument(storyDocId);
    }

    @Override
    public void renameDocument(StoryDocId storyDocId, String new_name) {
        StoryDoc storyDoc = domainService.getDocument(storyDocId);
        storyDoc.rename(new_name);
    }

    @Override
    public BlockId addArtifactBlock(StoryDocId storyDocId, String name) {

        StoryDoc storyDoc = domainService.getDocument(storyDocId);
        BlockId blockId = new BlockId(idGenerator.generateID("BLOCK"));

        return storyDoc.addBlock(blockId, name);

    }

    @Override
    public void renameBlock(BlockCoordinate blockCoordinate, String new_name) {
        StoryDoc storyDoc = domainService.getDocument(blockCoordinate.getStoryDocId());
        storyDoc.renameBlock(blockCoordinate, new_name);
    }

    @Override
    public BlockId addArtifactBlock(StoryDocId storyDocId, SectionId sectionId) {
        StoryDoc storyDoc = domainService.getDocument(storyDocId);
        BlockId blockId = new BlockId(idGenerator.generateID("BLOCK"));
        return storyDoc.addBlock(blockId, sectionId);
    }

    @Override
    public void moveBlock(BlockCoordinate blockToMove, BlockCoordinate newParent, int childIndexInParent) {
        StoryDoc storyDoc = domainService.getDocument(blockToMove.getStoryDocId());
        storyDoc.moveBlock(blockToMove, newParent, childIndexInParent);
    }

    @Override
    public void removeBlock(StoryDocId storyDocId, BlockId blockId) {
        StoryDoc storyDoc = domainService.getDocument(storyDocId);
        storyDoc.removeBlock(blockId);

    }

    @Override
    public SectionId addSection(StoryDocId storyDocId) {
        StoryDoc storyDoc = domainService.getDocument(storyDocId);
        SectionId sectionId = new SectionId(idGenerator.generateID("SECTION"));
        storyDoc.addSection(sectionId);
        return sectionId;
    }

    @Override
    public void addTag(StoryDocId storyDocId, BlockId blockId, String tag) {
        StoryDoc storyDoc = domainService.getDocument(storyDocId);
        storyDoc.addTag(blockId, tag);
    }

    @Override
    public void addArtifact(StoryDocId storyDocId, BlockId blockId, ArtifactId artifactId, String artifactType, String name) {
        Assert.notNull(name, "name is required");
        StoryDoc storyDoc = domainService.getDocument(storyDocId);
        ArtifactMetaData metaData = ArtifactMetaData.builder()
            .type(artifactType)
            .name(name)
            .build();
        storyDoc.addArtifact(blockId, artifactId, metaData);
    }

    @Override
    public void saveArtifact(ArtifactSaveContext context) {
        StoryDoc storyDoc = domainService.getDocument(context.getStoryDocId());
        storyDoc.saveArtifact(context);
    }

    @Override
    public <A extends Artifact> A loadArtifact(ArtifactLoadContext context) {
        StoryDoc storyDoc = domainService.getDocument(context.getBlockCoordinate().getStoryDocId());
        return storyDoc.loadArtifact(context);
    }

    @Override
    public void renameArtifact(BlockCoordinate blockCoordinate, ArtifactId artifactId, String new_name) {
        StoryDoc storyDoc = domainService.getDocument(blockCoordinate.getStoryDocId());
        storyDoc.renameArtifact(blockCoordinate, artifactId, new_name);

    }

    @Override
    public void saveBinaryArtifact(SaveBinaryArtifactContext context) throws IOException {
        StoryDoc storyDoc = domainService.getDocument(context.getCoordinate().getStoryDocId());
        storyDoc.saveBinaryArtifact(context);
    }

    @Override
    public ArtifactId createBinaryCollectionArtifact(BlockCoordinate coordinate, String artifactType, String binaryType, String name) {
        ArtifactId artifactId = ArtifactId.fromString(idGenerator.generateID(artifactType));
        CreateBinaryCollectionArtifactAction action = CreateBinaryCollectionArtifactAction.builder()
            .coordinate(coordinate)
            .artifactId(artifactId)
            .artifactName(name)
            .artifactType(artifactType)
            .binaryType(binaryType)
            .build();
        StoryDoc storyDoc = domainService.getDocument(coordinate.getStoryDocId());
        storyDoc.createBinaryCollectionArtifact(action);
        return artifactId;
    }

    // todo add filename generator
    @Override
    public ItemId addItemToBinaryCollection(BlockCoordinate coordinate, ArtifactId artifactId, String itemName, InputStream inputStream) {
        ItemId itemId = ItemId.fromString(idGenerator.generateID("item"));
        AddToBinaryCollectionAction action = AddToBinaryCollectionAction.builder()
            .coordinate(coordinate)
            .artifactId(artifactId)
            .itemId(itemId)
            .itemName(itemName)
            .inputStream(inputStream)
            .build();
        StoryDoc storyDoc = domainService.getDocument(coordinate.getStoryDocId());
        storyDoc.addItemToBinaryCollection(action);
        return itemId;
    }
}
