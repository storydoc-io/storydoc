package io.storydoc.server.code.app;

import io.storydoc.blueprint.BluePrint;
import io.storydoc.server.code.app.stitch.CodeTraceDTO;
import io.storydoc.server.code.app.stitch.StitchStructureDTO;
import io.storydoc.server.code.domain.*;
import io.storydoc.server.storydoc.app.dto.SettingsEntryDTO;
import io.storydoc.server.storydoc.domain.BlockCoordinate;
import io.storydoc.server.storydoc.domain.BlockId;
import io.storydoc.server.storydoc.domain.StoryDocId;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @PostMapping(value = "/settings/stitch", produces = MediaType.APPLICATION_JSON_VALUE)
    public void setStitchSettings(@RequestParam("stitchDir") String stitchDir) {
        codeService.setStitchSettings(stitchDir);
    }

    @GetMapping(value = "/settings/stitch", produces = MediaType.APPLICATION_JSON_VALUE)
    public SettingsEntryDTO getStitchSetttings() {
        return codeQueryService.getStitchSettings();
    }

    @PostMapping(value = "/codeexecution/stitch", produces = MediaType.APPLICATION_JSON_VALUE)
    public void setStitchDetailsForCodeExecution(@RequestParam("execStoryDocId") StoryDocId execStoryDocId, @RequestParam("execBlockId") BlockId execBlockId, @RequestParam("codeExecutionId") CodeExecutionId codeExecutionId,
                                                 @RequestParam("stitchFile") String stitchFile, @RequestParam("testClass") String testClass, @RequestParam("testMethod") String testMethod
    ) {
        CodeExecutionCoordinate codeExecutionCoordinate  = CodeExecutionCoordinate.of(BlockCoordinate.of(execStoryDocId, execBlockId), codeExecutionId);
        codeService.setStitchDetails(codeExecutionCoordinate, stitchFile, testClass, testMethod);
    }

    @GetMapping(value = "/source", produces = MediaType.APPLICATION_JSON_VALUE)
    public SourceCodeDTO source(@RequestParam("storyDocId") StoryDocId storyDocId, @RequestParam("blockId") BlockId blockId, @RequestParam("sourceCodeConfigId") SourceCodeConfigId sourceCodeConfigId, String className) {
        SourceCodeConfigCoordinate configCoordinate = SourceCodeConfigCoordinate.of(BlockCoordinate.of(storyDocId, blockId), sourceCodeConfigId);
        return codeService.getSource(className, configCoordinate);
    }

    @PostMapping(value = "/stitchconfig", produces = MediaType.APPLICATION_JSON_VALUE)
    public StitchConfigCoordinate createStitchConfig(@RequestParam("storyDocId") StoryDocId storyDocId, @RequestParam("blockId") BlockId blockId, @RequestParam("name") String name) {
        BlockCoordinate blockCoordinate = BlockCoordinate.of(storyDocId, blockId);
        return codeService.createStitchConfig(blockCoordinate, name);
    }

    @GetMapping(value = "/stitchconfig", produces = MediaType.APPLICATION_JSON_VALUE)
    public StitchConfigDTO getStitchConfig(@RequestParam("storyDocId") StoryDocId storyDocId, @RequestParam("blockId") BlockId blockId, @RequestParam("stitchConfigId") StitchConfigId stitchConfigId) {
        BlockCoordinate blockCoordinate = BlockCoordinate.of(storyDocId, blockId);
        return codeQueryService.getStitchConfig(StitchConfigCoordinate.of(blockCoordinate, stitchConfigId));
    }

    @PostMapping(value = "/stitchconfig/path", produces = MediaType.APPLICATION_JSON_VALUE)
    public void setStitchPath(@RequestParam("storyDocId") StoryDocId storyDocId, @RequestParam("blockId") BlockId blockId, @RequestParam("stitchConfigId") StitchConfigId stitchConfigId, @RequestParam("path") String path) {
        BlockCoordinate blockCoordinate = BlockCoordinate.of(storyDocId, blockId);
        codeService.setStitchPath(StitchConfigCoordinate.of(blockCoordinate, stitchConfigId), path);
    }

    @GetMapping(value = "/stitchstructure", produces = MediaType.APPLICATION_JSON_VALUE)
    public StitchStructureDTO getStitchStructure(@RequestParam("storyDocId") StoryDocId storyDocId, @RequestParam("blockId") BlockId blockId, @RequestParam("stitchConfigId") StitchConfigId stitchConfigId) {
        BlockCoordinate blockCoordinate = BlockCoordinate.of(storyDocId, blockId);
        return codeQueryService.getStitchStructure(StitchConfigCoordinate.of(blockCoordinate, stitchConfigId));
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

    @GetMapping(value="/blueprint" , produces = MediaType.APPLICATION_JSON_VALUE)
    public BluePrint getBluePrint() {
        return codeService.getBluePrint();
    }

    @GetMapping(value="/classify" , produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> classify(String className) {
        return codeService.classify(className);
    }

    @PostMapping(value="/classifymultiple" , produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, List<String>> classifyMultiple(@RequestBody String[] classNames) {
        return codeService.classifyMultiple(classNames);
    }

}
