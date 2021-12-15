package io.storydoc.server.document.app;

import io.storydoc.server.document.domain.*;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentDomainService domainService;

    public DocumentServiceImpl(DocumentDomainService domainService) {
        this.domainService = domainService;
    }

    @Override
    public StoryDocId createDocument() {
        StoryDocId storyDocId = new StoryDocId(UUID.randomUUID());
        return domainService.createDocument(storyDocId);
    }

    @Override
    public BlockId addArtifactBlock(StoryDocId storyDocId) {

        Document document = domainService.getDocument(storyDocId);
        BlockId blockId = new BlockId(UUID.randomUUID().toString());

        return document.addBlock(blockId);

    }

    @Override
    public BlockId addArtifactBlock(StoryDocId storyDocId, SectionId sectionId) {
        Document document = domainService.getDocument(storyDocId);
        BlockId blockId = new BlockId(UUID.randomUUID().toString());
        return document.addBlock(blockId, sectionId);
    }

    @Override
    public void removeBlock(StoryDocId storyDocId, BlockId blockId) {
        Document document = domainService.getDocument(storyDocId);
        document.removeBlock(blockId);

    }

    @Override
    public SectionId addSection(StoryDocId storyDocId) {
        Document document = domainService.getDocument(storyDocId);
        SectionId sectionId = new SectionId(UUID.randomUUID().toString());
        document.addSection(sectionId);
        return sectionId;
    }

    @Override
    public void addTag(StoryDocId storyDocId, BlockId blockId, String tag) {
        Document document = domainService.getDocument(storyDocId);
        document.addTag(blockId, tag);
    }
}
