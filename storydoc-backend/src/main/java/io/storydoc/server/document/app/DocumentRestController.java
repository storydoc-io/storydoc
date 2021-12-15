package io.storydoc.server.document.app;

import io.storydoc.server.document.app.dto.StoryDocDTO;
import io.storydoc.server.document.domain.BlockId;
import io.storydoc.server.document.domain.StoryDocId;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/document")
public class DocumentRestController {

    private final DocumentService documentService;

    private final DocumentQueryService documentQueryService;

    public DocumentRestController(DocumentService documentService, DocumentQueryService documentQueryService) {
        this.documentService = documentService;
        this.documentQueryService = documentQueryService;
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public StoryDocDTO getDocument(StoryDocId storyDocId) {
        return documentQueryService.getDocument(storyDocId);
    }

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public StoryDocId createDocument() {
        return documentService.createDocument();
    }

    @PostMapping(value = "/addblock", produces = MediaType.APPLICATION_JSON_VALUE)
    public BlockId addBlock(StoryDocId storyDocId) {
        return documentService.addArtifactBlock(storyDocId);
    }

    @PostMapping(value = "/removeblock", produces = MediaType.APPLICATION_JSON_VALUE)
    public void removeBlock(StoryDocId storyDocId, BlockId blockId1) {
        documentService.removeBlock(storyDocId, blockId1);
    }

    @PostMapping(value = "/tag", produces = MediaType.APPLICATION_JSON_VALUE)
    public void addTag(StoryDocId storyDocId, BlockId blockId, String tag) {
        documentService.addTag(storyDocId, blockId, tag);
    }


}
