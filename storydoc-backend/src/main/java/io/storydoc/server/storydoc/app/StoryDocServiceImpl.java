package io.storydoc.server.storydoc.app;

import io.storydoc.server.infra.IDGenerator;
import io.storydoc.server.storydoc.domain.*;
import io.storydoc.server.storydoc.domain.action.ArtifactLoadContext;
import io.storydoc.server.storydoc.domain.action.ArtifactSaveContext;
import io.storydoc.server.storydoc.domain.action.SaveBinaryArtifactContext;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;


@Service
public class StoryDocServiceImpl implements StoryDocService {

    private final StoryDocDomainService domainService;

    private final IDGenerator idGenerator;

    public StoryDocServiceImpl(StoryDocDomainService domainService, IDGenerator idGenerator) {
        this.domainService = domainService;
        this.idGenerator = idGenerator;
    }

    @Override
    public StoryDocId createDocument() {
        StoryDocId storyDocId = new StoryDocId(idGenerator.generateID("STORY"));
        return domainService.createDocument(storyDocId);
    }

    @Override
    public BlockId addArtifactBlock(StoryDocId storyDocId) {

        StoryDoc storyDoc = domainService.getDocument(storyDocId);
        BlockId blockId = new BlockId(idGenerator.generateID("BLOCK"));

        return storyDoc.addBlock(blockId);

    }

    @Override
    public BlockId addArtifactBlock(StoryDocId storyDocId, SectionId sectionId) {
        StoryDoc storyDoc = domainService.getDocument(storyDocId);
        BlockId blockId = new BlockId(idGenerator.generateID("BLOCK"));
        return storyDoc.addBlock(blockId, sectionId);
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
        StoryDoc storyDoc = domainService.getDocument(context.getStoryDocId());
        return storyDoc.loadArtifact(context);
    }

    @Override
    public void saveBinaryArtifact(SaveBinaryArtifactContext context) throws IOException {
        StoryDoc storyDoc = domainService.getDocument(context.getCoordinate().getStoryDocId());
        storyDoc.saveBinaryArtifact(context);
    }
}
