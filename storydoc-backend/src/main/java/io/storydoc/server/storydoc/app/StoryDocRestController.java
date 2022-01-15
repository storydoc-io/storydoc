package io.storydoc.server.storydoc.app;

import io.storydoc.server.storydoc.app.dto.StoryDocDTO;
import io.storydoc.server.storydoc.domain.BlockId;
import io.storydoc.server.storydoc.domain.StoryDocId;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/storydoc")
public class StoryDocRestController {

    private final StoryDocService storyDocService;

    private final StoryDocQueryService storyDocQueryService;

    public StoryDocRestController(StoryDocService storyDocService, StoryDocQueryService storyDocQueryService) {
        this.storyDocService = storyDocService;
        this.storyDocQueryService = storyDocQueryService;
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public StoryDocDTO getDocument(StoryDocId storyDocId) {
        return storyDocQueryService.getDocument(storyDocId);
    }

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public StoryDocId createDocument() {
        return storyDocService.createDocument();
    }

    @PostMapping(value = "/addblock", produces = MediaType.APPLICATION_JSON_VALUE)
    public BlockId addBlock(StoryDocId storyDocId) {
        return storyDocService.addArtifactBlock(storyDocId);
    }

    @PostMapping(value = "/removeblock", produces = MediaType.APPLICATION_JSON_VALUE)
    public void removeBlock(StoryDocId storyDocId, BlockId blockId1) {
        storyDocService.removeBlock(storyDocId, blockId1);
    }

    @PostMapping(value = "/tag", produces = MediaType.APPLICATION_JSON_VALUE)
    public void addTag(StoryDocId storyDocId, BlockId blockId, String tag) {
        storyDocService.addTag(storyDocId, blockId, tag);
    }


}
