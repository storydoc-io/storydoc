package io.storydoc.server.code.app;

import io.storydoc.server.TestBase;
import io.storydoc.server.code.domain.CodeExecutionCoordinate;
import io.storydoc.server.storydoc.StoryDocTestUtils;
import io.storydoc.server.storydoc.app.dto.StoryDocDTO;
import io.storydoc.server.storydoc.domain.BlockCoordinate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class CodeServiceTest extends TestBase {

    @Autowired
    CodeService codeService;

    @Autowired
    StoryDocTestUtils storyDocTestUtils;


    @Test
    public void create_code_execution() {
        // given a storydoc with a artifact block
        BlockCoordinate blockCoordinate = storyDocTestUtils.create_storydoc_with_artifact_block();

        // when I create a code execution
        String name = "name";
        CodeExecutionCoordinate coordinate = codeService.createCodeExecution(blockCoordinate, name);

        // the artifact is part of the block
        StoryDocDTO storyDocDTO = storyDocTestUtils.getStorydoc(blockCoordinate.getStoryDocId());
        assertEquals(1, storyDocDTO.getBlocks().get(0).getArtifacts().size());

    }

    @Test
    public void load_code() {
        codeService.getExecution();

    }


}