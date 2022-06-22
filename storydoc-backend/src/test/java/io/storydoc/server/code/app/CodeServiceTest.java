package io.storydoc.server.code.app;

import io.storydoc.server.TestBase;
import io.storydoc.server.code.app.stitch.CodeTraceDTO;
import io.storydoc.server.code.domain.CodeExecutionCoordinate;
import io.storydoc.server.code.domain.SourceCodeConfigCoordinate;
import io.storydoc.server.code.domain.StitchConfigCoordinate;
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
    public void stitch_config__create() {
        // given a storydoc with a artifact block
        BlockCoordinate blockCoordinate = storyDocTestFixture.create_storydoc_with_artifact_block();

        // when I create a stitch config
        String name = "name";
        StitchConfigCoordinate coordinate = codeService.createStitchConfig(blockCoordinate, name);

        workspaceTestFixture.logResourceContent("after add stitch config", storyDocQueryService.getDocument(blockCoordinate.getStoryDocId()).getUrn());

        // then the artifact is part of the storydoc
        StoryDocDTO storyDocDTO = storyDocTestFixture.getStorydoc(blockCoordinate.getStoryDocId());
        assertEquals(1, storyDocDTO.getBlocks().get(0).getArtifacts().size());

        // and then I get the config  from its coordinate
        StitchConfigDTO dto = codeQueryService.getStitchConfig(coordinate);
        assertNotNull(dto);
        assertEquals(coordinate, dto.getStitchConfigCoordinate());
        assertNull(dto.getDir());

    }

    @Test
    public void stitch_config__set_source_path() {
        // given a storydoc with a artifact block
        BlockCoordinate blockCoordinate = storyDocTestFixture.create_storydoc_with_artifact_block();

        // given a stitch config
        String name = "name";
        StitchConfigCoordinate coordinate = codeService.createStitchConfig(blockCoordinate, name);

        // when I set a stitch path in the code config
        String path = "/some/path";
        codeService.setStitchPath(coordinate, path);

        // then the config contains the code path
        StitchConfigDTO dto = codeQueryService.getStitchConfig(coordinate);
        assertEquals(path, dto.getDir());
    }
    
    
    @Test
    public void sourcecode_config__create() {
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
    public void sourcecode_config__set_source_path() {
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
    public void code_execution__create() {
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
    public void code_execution__set_stitch_details() {
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
    public void code_execution__get_execution() {
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
    public void code_execution__use_default_source_code_config() {
        // given a storydoc with a artifact block
        BlockCoordinate blockCoordinate = storyDocTestFixture.create_storydoc_with_artifact_block();

        // given a code execution
        String name = "test-1";
        CodeExecutionCoordinate codeExecutionCoordinate = codeService.createCodeExecution(blockCoordinate, name);

        // the code execution has no sourcecode config
        {
            CodeTraceDTO codeTraceDTO = codeQueryService.getExecution(codeExecutionCoordinate);
            assertNull(codeTraceDTO.getConfig());
        }

        // When I create a sourcecode config in the same block
        String sourceCodeConfigName = "source-code-config-name";
        SourceCodeConfigCoordinate sourceCodeConfigCoordinate = codeService.createSourceCodeConfig(blockCoordinate, sourceCodeConfigName);

        // then the code execution has a default association with the sourcecode config
        {
            CodeTraceDTO codeTraceDTO = codeQueryService.getExecution(codeExecutionCoordinate);
            assertNotNull(codeTraceDTO.getConfig());
            assertEquals(sourceCodeConfigCoordinate, codeTraceDTO.getConfig());
        }

    }

    @Test
    public void code_execution__use_default_stitch_config() {
        // given a storydoc with a artifact block
        BlockCoordinate blockCoordinate = storyDocTestFixture.create_storydoc_with_artifact_block();

        // given a code execution
        String name = "test-1";
        CodeExecutionCoordinate codeExecutionCoordinate = codeService.createCodeExecution(blockCoordinate, name);

        // the code execution has no stitch config
        {
            CodeTraceDTO codeTraceDTO = codeQueryService.getExecution(codeExecutionCoordinate);
            assertNull(codeTraceDTO.getStitchConfigCoordinate());
        }

        // When I create a stitch config in the same block
        String stitchConfigName = "stitch-config-name";
        StitchConfigCoordinate stitchConfigCoordinate = codeService.createStitchConfig(blockCoordinate, stitchConfigName);

        // then the code execution has a default association with the stitch config
        {
            CodeTraceDTO codeTraceDTO = codeQueryService.getExecution(codeExecutionCoordinate);
            assertNotNull(codeTraceDTO.getStitchConfigCoordinate());
            assertEquals(stitchConfigCoordinate, codeTraceDTO.getStitchConfigCoordinate());
        }

    }

}