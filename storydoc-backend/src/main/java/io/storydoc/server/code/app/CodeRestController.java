package io.storydoc.server.code.app;

import io.storydoc.server.code.app.stitch.CodeTraceDTO;
import io.storydoc.server.code.domain.CodeExecutionCoordinate;
import io.storydoc.server.code.domain.CodeExecutionId;
import io.storydoc.server.code.domain.SourceCodeConfigCoordinate;
import io.storydoc.server.code.domain.SourceCodeConfigId;
import io.storydoc.server.storydoc.domain.BlockCoordinate;
import io.storydoc.server.storydoc.domain.BlockId;
import io.storydoc.server.storydoc.domain.StoryDocId;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/code")
public class CodeRestController {

    private final CodeService codeService;

    private final CodeQueryService codeQueryService;

    public CodeRestController(CodeService codeService, CodeQueryService codeQueryService) {
        this.codeService = codeService;
        this.codeQueryService = codeQueryService;
    }

    @PostMapping(value = "/codeexecution", produces = MediaType.APPLICATION_JSON_VALUE)
    public CodeExecutionCoordinate createCodeExecution(@RequestParam("storyDocId") StoryDocId storyDocId, @RequestParam("blockId") BlockId blockId, @RequestParam("name") String name) {
        BlockCoordinate blockCoordinate = BlockCoordinate.of(storyDocId, blockId);
        return codeService.createCodeExecution(blockCoordinate, name);
    }

    @GetMapping(value = "/codeexecution", produces = MediaType.APPLICATION_JSON_VALUE)
    public CodeTraceDTO getCodeExecution(@RequestParam("storyDocId") StoryDocId storyDocId, @RequestParam("blockId") BlockId blockId, @RequestParam("codeExecutionId") CodeExecutionId codeExecutionId) {
        BlockCoordinate blockCoordinate = BlockCoordinate.of(storyDocId, blockId);
        return codeQueryService.getExecution(CodeExecutionCoordinate.of(blockCoordinate, codeExecutionId));
    }

    @PostMapping(value = "/codeexecution/config", produces = MediaType.APPLICATION_JSON_VALUE)
    public void setConfigForCodeExecution(@RequestParam("execStoryDocId") StoryDocId execStoryDocId, @RequestParam("execBlockId") BlockId execBlockId, @RequestParam("codeExecutionId") CodeExecutionId codeExecutionId,
                                          @RequestParam("configStoryDocId") StoryDocId configStoryDocId, @RequestParam("configBlockId") BlockId configBlockId, @RequestParam("sourceCodeConfigId") SourceCodeConfigId sourceCodeConfigId
    ) {
        CodeExecutionCoordinate codeExecutionCoordinate  = CodeExecutionCoordinate.of(BlockCoordinate.of(execStoryDocId, execBlockId), codeExecutionId);
        SourceCodeConfigCoordinate sourceCodeConfigCoordinate = SourceCodeConfigCoordinate.of(BlockCoordinate.of(configStoryDocId, configBlockId), sourceCodeConfigId);
        codeService.setSourceConfigForExecution(codeExecutionCoordinate, sourceCodeConfigCoordinate);
    }

    @GetMapping(value = "/source", produces = MediaType.APPLICATION_JSON_VALUE)
    public SourceCodeDTO source(@RequestParam("storyDocId") StoryDocId storyDocId, @RequestParam("blockId") BlockId blockId, @RequestParam("sourceCodeConfigId") SourceCodeConfigId sourceCodeConfigId, String className) {
        SourceCodeConfigCoordinate configCoordinate = SourceCodeConfigCoordinate.of(BlockCoordinate.of(storyDocId, blockId), sourceCodeConfigId);
        return codeService.getSource(className, configCoordinate);
    }

    @PostMapping(value = "/sourcecodeconfig", produces = MediaType.APPLICATION_JSON_VALUE)
    public SourceCodeConfigCoordinate createSourceConfig(@RequestParam("storyDocId") StoryDocId storyDocId, @RequestParam("blockId") BlockId blockId, @RequestParam("name") String name) {
        BlockCoordinate blockCoordinate = BlockCoordinate.of(storyDocId, blockId);
        return codeService.createSourceCodeConfig(blockCoordinate, name);
    }

    @GetMapping(value = "/sourcecodeconfig", produces = MediaType.APPLICATION_JSON_VALUE)
    public SourceCodeConfigDTO getSourceConfig(@RequestParam("storyDocId") StoryDocId storyDocId, @RequestParam("blockId") BlockId blockId, @RequestParam("sourceCodeConfigId") SourceCodeConfigId sourceCodeConfigId) {
        BlockCoordinate blockCoordinate = BlockCoordinate.of(storyDocId, blockId);
        return codeQueryService.getSourceCodeConfig(SourceCodeConfigCoordinate.of(blockCoordinate, sourceCodeConfigId));
    }

    @PostMapping(value = "/sourcecodeconfig/path", produces = MediaType.APPLICATION_JSON_VALUE)
    public void setSourcePath(@RequestParam("storyDocId") StoryDocId storyDocId, @RequestParam("blockId") BlockId blockId, @RequestParam("sourceCodeConfigId") SourceCodeConfigId sourceCodeConfigId, @RequestParam("path") String path) {
        BlockCoordinate blockCoordinate = BlockCoordinate.of(storyDocId, blockId);
        codeService.setSourcePath(SourceCodeConfigCoordinate.of(blockCoordinate, sourceCodeConfigId), path);
    }

}
