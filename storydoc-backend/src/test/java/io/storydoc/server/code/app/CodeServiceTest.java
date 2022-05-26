package io.storydoc.server.code.app;

import io.storydoc.server.TestBase;
import io.storydoc.server.code.app.stitch.CodeTraceDTO;
import io.storydoc.server.code.domain.CodeExecutionCoordinate;
import io.storydoc.server.code.domain.SourceCodeConfigCoordinate;
import io.storydoc.server.storydoc.StoryDocTestFixture;
import io.storydoc.server.storydoc.app.StoryDocQueryService;
import io.storydoc.server.storydoc.app.dto.StoryDocDTO;
import io.storydoc.server.storydoc.domain.BlockCoordinate;
import io.storydoc.server.workspace.WorkspaceTestFixture;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class CodeServiceTest extends TestBase {

    @Autowired
    CodeService codeService;

    @Autowired
    CodeQueryService codeQueryService;

    @Autowired
    StoryDocTestFixture storyDocTestFixture;

    @Autowired
    WorkspaceTestFixture workspaceTestFixture;

    @Autowired
    StoryDocQueryService storyDocQueryService;

    @Test
    public void create_sourcecode_config() {
        // given a storydoc with a artifact block
        BlockCoordinate blockCoordinate = storyDocTestFixture.create_storydoc_with_artifact_block();

        // when I create a code config
        String name = "name";
        SourceCodeConfigCoordinate coordinate = codeService.createSourceCodeConfig(blockCoordinate, name);

        workspaceTestFixture.logResourceContent("after add code config", storyDocQueryService.getDocument(blockCoordinate.getStoryDocId()).getUrn());

        // then the artifact is part of the storydoc
        StoryDocDTO storyDocDTO = storyDocTestFixture.getStorydoc(blockCoordinate.getStoryDocId());
        assertEquals(1, storyDocDTO.getBlocks().get(0).getArtifacts().size());

        // and then I get the config  from its coordinate
        SourceCodeConfigDTO dto = codeQueryService.getSourceCodeConfig(coordinate);
        assertNotNull(dto);
        assertEquals(coordinate.getSourceCodeConfigId(), dto.getId());
        assertNotNull(dto.getDirs());
        assertEquals(0, dto.getDirs().size());
    }

    @Test
    public void set_source_code_path() {
        // given a storydoc with a artifact block
        BlockCoordinate blockCoordinate = storyDocTestFixture.create_storydoc_with_artifact_block();

        // given a sourcecode config
        String name = "name";
        SourceCodeConfigCoordinate coordinate = codeService.createSourceCodeConfig(blockCoordinate, name);

        // when I set a sourcecode path in the code config
        String path = "/some/path";
        codeService.setSourcePath(coordinate, path);

        // then the config contains the code path
        SourceCodeConfigDTO dto = codeQueryService.getSourceCodeConfig(coordinate);
        assertEquals(1, dto.getDirs().size());
        assertEquals(path, dto.getDirs().get(0));
    }

    @Test
    public void create_code_execution() {
        // given a storydoc with a artifact block
        BlockCoordinate blockCoordinate = storyDocTestFixture.create_storydoc_with_artifact_block();

        // when I create a code execution
        String name = "name";
        CodeExecutionCoordinate coordinate = codeService.createCodeExecution(blockCoordinate, name);

        // the artifact is part of the block
        StoryDocDTO storyDocDTO = storyDocTestFixture.getStorydoc(blockCoordinate.getStoryDocId());
        assertEquals(1, storyDocDTO.getBlocks().get(0).getArtifacts().size());

    }

    @Test
    public void set_stitch_details() {
        // given a storydoc with a artifact block
        BlockCoordinate blockCoordinate = storyDocTestFixture.create_storydoc_with_artifact_block();

        // given a code execution
        String name = "name";
        CodeExecutionCoordinate coordinate = codeService.createCodeExecution(blockCoordinate, name);

        // when I set the stitch details
        String stitchFile = "path/to/stitchfile";
        String testClass = "test.com.MyClass";
        String testMethod = "testMethod";
        codeService.setStitchDetails(coordinate, stitchFile, testClass, testMethod);
    }

    @Test
    public void parse_stitch_file() {
        // given a storydoc with a artifact block
        BlockCoordinate blockCoordinate = storyDocTestFixture.create_storydoc_with_artifact_block();

        // given a code execution
        String name = "name";
        CodeExecutionCoordinate coordinate = codeService.createCodeExecution(blockCoordinate, name);

        // given the stitch details are set
        String stitchFile = "src/test/resources/io/storydoc/server/code/infra/sample-stitch-file.txt";
        String lineFrom = "TestScenario|TestEntered|{ \"testCaseName\": \"io.storydoc.server.ui.app.UIServiceTest createUIScenario(io.storydoc.server.ui.app.UIServiceTest)\"";
        String lineTo = "TestScenario|TestFinished|{ \"testCaseName\": \"io.storydoc.server.ui.app.UIServiceTest createUIScenario(io.storydoc.server.ui.app.UIServiceTest)\"";
        codeService.setStitchDetails(coordinate, stitchFile, lineFrom, lineTo);

        // the I can acces the code trace for this execution
        CodeTraceDTO codeTraceDTO = codeQueryService.getExecution(coordinate);
        assertNotNull(codeTraceDTO);
        assertTrue(codeTraceDTO.getItems().size() > 0);
    }


        @Test
    public void set_source_code_config_for_code_execution() {
        // given a storydoc with a artifact block
        BlockCoordinate blockCoordinate = storyDocTestFixture.create_storydoc_with_artifact_block();

        // given a code execution
        String name = "test-1";
        CodeExecutionCoordinate codeExecutionCoordinate = codeService.createCodeExecution(blockCoordinate, name);

        // given a sourcecode config
        String sourceCodeConfigName = "source-code-config-name";
        SourceCodeConfigCoordinate sourceCodeConfigCoordinate = codeService.createSourceCodeConfig(blockCoordinate, sourceCodeConfigName);

        // when I associate the ccode exection with the sourcecode config
        codeService.setSourceConfigForExecution(codeExecutionCoordinate, sourceCodeConfigCoordinate);
        workspaceTestFixture.logResourceContent("after association", storyDocQueryService.getDocument(blockCoordinate.getStoryDocId()).getUrn());

        // I can find the associated config
        CodeTraceDTO codeTraceDTO =codeQueryService.getExecution(codeExecutionCoordinate);
        assertNotNull(codeTraceDTO.getConfig());
        assertEquals(sourceCodeConfigCoordinate, codeTraceDTO.getConfig());

    }

}