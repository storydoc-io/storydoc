package io.storydoc.server.storydoc.app;

import io.storydoc.server.storydoc.app.dto.StoryDocDTO;
import io.storydoc.server.storydoc.app.dto.StoryDocSummaryDTO;
import io.storydoc.server.storydoc.domain.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping(value = "/storydocs", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StoryDocSummaryDTO> getDocuments() {
        return storyDocQueryService.getStoryDocs();
    }

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public StoryDocId createDocument(String name) {
        return storyDocService.createDocument(name);
    }

    @PutMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public void changeDocumentName(@RequestParam("storyDocId") StoryDocId storyDocId, @RequestParam("name") String name) {
        storyDocService.renameDocument(storyDocId,name);
    }

    @DeleteMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public void removeDocument(@RequestParam("storyDocId") StoryDocId storyDocId) {
        storyDocService.removeDocument(storyDocId);
    }

    @PostMapping(value = "/addblock", produces = MediaType.APPLICATION_JSON_VALUE)
    public BlockId addBlock(StoryDocId storyDocId, String name) {
        return storyDocService.addArtifactBlock(storyDocId, name);
    }

    @PutMapping(value = "/renameblock", produces = MediaType.APPLICATION_JSON_VALUE)
    public void renameBlock(@RequestParam("storyDocId") StoryDocId storyDocId, @RequestParam("blockId")  BlockId blockId, @RequestParam("name") String name) {
        BlockCoordinate blockCoordinate = BlockCoordinate.of(storyDocId,blockId);
        storyDocService.renameBlock(blockCoordinate, name);
    }

    @PutMapping(value = "/renameartifact", produces = MediaType.APPLICATION_JSON_VALUE)
    public void renameArtifact(@RequestParam("storyDocId") StoryDocId storyDocId, @RequestParam("blockId")  BlockId blockId, @RequestParam("artifactId") ArtifactId artifactId, @RequestParam("name") String name) {
        BlockCoordinate blockCoordinate = BlockCoordinate.of(storyDocId,blockId);
        storyDocService.renameArtifact(blockCoordinate, artifactId, name);
    }

    @DeleteMapping(value = "/removeartifact", produces = MediaType.APPLICATION_JSON_VALUE)
    public void removeArtifact(@RequestParam("storyDocId") StoryDocId storyDocId, @RequestParam("blockId")  BlockId blockId, @RequestParam("artifactId") ArtifactId artifactId) {
        BlockCoordinate blockCoordinate = BlockCoordinate.of(storyDocId,blockId);
        ArtifactCoordinate artifactCoordinate = ArtifactCoordinate.of(artifactId, blockCoordinate);
        storyDocService.removeArtifact(artifactCoordinate);
    }

    @DeleteMapping(value = "/removeblock", produces = MediaType.APPLICATION_JSON_VALUE)
    public void removeBlock(@RequestParam("storyDocId")  StoryDocId storyDocId, @RequestParam("blockId") BlockId blockId) {
        BlockCoordinate blockCoordinate = BlockCoordinate.of(storyDocId, blockId);
        storyDocService.removeBlock(blockCoordinate);
    }

    @PostMapping(value = "/tag", produces = MediaType.APPLICATION_JSON_VALUE)
    public void addTag(StoryDocId storyDocId, BlockId blockId, String tag) {
        storyDocService.addTag(storyDocId, blockId, tag);
    }


}
