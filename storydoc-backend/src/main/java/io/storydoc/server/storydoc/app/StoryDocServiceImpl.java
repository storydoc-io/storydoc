package io.storydoc.server.storydoc.app;

import io.storydoc.server.infra.IDGenerator;
import io.storydoc.server.storydoc.app.dto.SettingsEntryDTO;
import io.storydoc.server.storydoc.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


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
    public void removeBlock(BlockCoordinate blockCoordinate) {
        StoryDoc storyDoc = domainService.getDocument(blockCoordinate.getStoryDocId());
        storyDoc.removeBlock(blockCoordinate);

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
    public void addArtifact(BlockCoordinate blockCoordinate, ArtifactId artifactId, String artifactType, String name) {
        Assert.notNull(name, "name is required");
        Assert.notNull(artifactType, "artifactType is required");
        StoryDoc storyDoc = domainService.getDocument(blockCoordinate.getStoryDocId());
        ArtifactMetaData metaData = ArtifactMetaData.builder()
            .type(artifactType)
            .name(name)
            .collection(false)
            .binary(false)
            .build();
        storyDoc.addArtifact(blockCoordinate, artifactId, metaData);
    }

    @Override
    public void changeArtifactState(ArtifactCoordinate coordinate, ArtifactState state) {
        StoryDoc storyDoc = domainService.getDocument(coordinate.getBlockCoordinate().getStoryDocId());
        storyDoc.changeArtifactState(coordinate, state);
    }

    @Override
    public void saveArtifact(ArtifactCoordinate coordinate, ArtifactSerializer serializer) {
        StoryDoc storyDoc = domainService.getDocument(coordinate.getBlockCoordinate().getStoryDocId());
        storyDoc.saveArtifact(coordinate, serializer);
    }

    @Override
    public <A extends Artifact> A loadArtifact(ArtifactCoordinate coordinate, ArtifactDeserializer<A> deserializer) {
        StoryDoc storyDoc = domainService.getDocument(coordinate.getBlockCoordinate().getStoryDocId());
        return storyDoc.loadArtifact(coordinate, deserializer);
    }

    @Override
    public void renameArtifact(BlockCoordinate blockCoordinate, ArtifactId artifactId, String new_name) {
        StoryDoc storyDoc = domainService.getDocument(blockCoordinate.getStoryDocId());
        storyDoc.renameArtifact(blockCoordinate, artifactId, new_name);

    }

    @Override
    public void addAssociation(ArtifactCoordinate artifactCoordinateFrom, ArtifactCoordinate artifactCoordinateTo, String name) {
        StoryDoc storyDoc = domainService.getDocument(artifactCoordinateFrom.getBlockCoordinate().getStoryDocId());
        storyDoc.addAssociation(artifactCoordinateFrom, artifactCoordinateTo, name);
    }

    @Override
    public void saveBinaryArtifact(ArtifactCoordinate artifactCoordinate, InputStream inputStream) throws IOException {
        StoryDoc storyDoc = domainService.getDocument(artifactCoordinate.getBlockCoordinate().getStoryDocId());
        storyDoc.saveBinaryArtifact(artifactCoordinate, inputStream);
    }

    @Override
    public void createBinaryCollectionArtifact(BlockCoordinate coordinate, ArtifactId artifactId, String artifactType, String binaryType, String name) {
        StoryDoc storyDoc = domainService.getDocument(coordinate.getStoryDocId());
        ArtifactMetaData metaData = ArtifactMetaData.builder()
                .type(artifactType)
                .name(name)
                .binary(true)
                .collection(true)
                .binaryType(binaryType)
                .build();
        storyDoc.addArtifact(coordinate, artifactId, metaData);
    }

    // todo
    // -  item metadata (filesize..)
    //  - filename extension
    @Override
    public ItemId addItemToBinaryCollection(BlockCoordinate coordinate, ArtifactId artifactId, String itemName, InputStream inputStream) {
        ItemId itemId = ItemId.fromString(idGenerator.generateID("item"));
        StoryDoc storyDoc = domainService.getDocument(coordinate.getStoryDocId());
        storyDoc.addItemToBinaryCollection(ArtifactCoordinate.of(coordinate, artifactId), itemName, itemId, inputStream);
        return itemId;
    }

    @Override
    public void renameItemFromBinaryCollection(ArtifactCoordinate artifactCoordinate, ItemId itemId, String name) {
        StoryDoc storyDoc = domainService.getDocument(artifactCoordinate.getBlockCoordinate().getStoryDocId());
        storyDoc.renameItemFromBinaryCollection(artifactCoordinate, itemId, name);
    }

    @Override
    public void removeItemFromBinaryCollection(ArtifactCoordinate artifactCoordinate, ItemId itemId) {
        StoryDoc storyDoc = domainService.getDocument(artifactCoordinate.getBlockCoordinate().getStoryDocId());
        storyDoc.removeItemFromBinaryCollection(artifactCoordinate, itemId);
    }

    @Override
    public void removeArtifact(ArtifactCoordinate artifactCoordinate) {
        StoryDoc storyDoc = domainService.getDocument(artifactCoordinate.getBlockCoordinate().getStoryDocId());
        storyDoc.deleteArtifact(artifactCoordinate);
    }

    @Override
    public void setGlobalSetting(SettingsEntryDTO setting) {
        Settings settings = domainService.getSettings();
        settings.add(setting);
    }

    @Override
    public void setGlobalSettings(List<SettingsEntryDTO> settingDTOs) {
        Settings settings = domainService.getSettings();
        settings.addAll(settingDTOs);
    }
}
