package io.storydoc.server.code.app;

import io.storydoc.server.code.domain.CodeExecutionCoordinate;
import io.storydoc.server.storydoc.domain.BlockCoordinate;
import io.storydoc.server.storydoc.domain.BlockId;
import io.storydoc.server.storydoc.domain.StoryDocId;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/code")
public class CodeRestController {

    private final CodeService codeService;

    public CodeRestController(CodeService codeService) {
        this.codeService = codeService;
    }

    @PostMapping(value = "/codeexecution", produces = MediaType.APPLICATION_JSON_VALUE)
    public CodeExecutionCoordinate createCodeExecution(@RequestParam("storyDocId") StoryDocId storyDocId, @RequestParam("blockId") BlockId blockId, @RequestParam("name") String name) {
        BlockCoordinate blockCoordinate = BlockCoordinate.of(storyDocId, blockId);
        return codeService.createCodeExecution(blockCoordinate, name);
    }


    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public CodeTraceDTO trace() {
        return codeService.getExecution();
    }

    @GetMapping(value = "/source", produces = MediaType.APPLICATION_JSON_VALUE)
    public SourceCodeDTO source(String className) {
        return codeService.getSource(className);
    }


}
