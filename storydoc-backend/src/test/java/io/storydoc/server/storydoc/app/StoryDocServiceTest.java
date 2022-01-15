package io.storydoc.server.storydoc.app;

import io.storydoc.server.TestBase;
import io.storydoc.server.storydoc.app.dto.BlockDTO;
import io.storydoc.server.storydoc.app.dto.StoryDocDTO;
import io.storydoc.server.storydoc.domain.BlockId;
import io.storydoc.server.storydoc.domain.SectionId;
import io.storydoc.server.storydoc.domain.StoryDocId;
import io.storydoc.server.workspace.app.WorkspaceQueryService;
import io.storydoc.server.workspace.WorkspaceTestUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Slf4j
public class StoryDocServiceTest extends TestBase {

    @Autowired
    private StoryDocService storyDocService;

    @Autowired
    private StoryDocQueryService storyDocQueryService;

    @Autowired
    private WorkspaceQueryService workspaceQueryService;


    private WorkspaceTestUtils workspaceTestUtils;

    @Before
    public void setup() {
        workspaceTestUtils = new WorkspaceTestUtils(workspaceQueryService);
    }

    @Test
    public void createDocument() {

        // when
        StoryDocId storyDocId = storyDocService.createDocument();

        // then
        assertNotNull(storyDocId);

        StoryDocDTO dto = storyDocQueryService.getDocument(storyDocId);

        assertNotNull(dto);
        assertNotNull(dto.getUrn());
        assertEquals(storyDocId, dto.getStoryDocId());
        assertNotNull(dto.getBlocks());

        workspaceTestUtils.logFolderStructure("after creating document ");
        workspaceTestUtils.logResourceContent("storydoc", storyDocQueryService.getDocument(storyDocId).getUrn());


    }

    @Test
    public void addArtifactBlock() {
        // given
        StoryDocId storyDocId = storyDocService.createDocument();

        // when
        BlockId blockId = storyDocService.addArtifactBlock(storyDocId);

        // then
        assertNotNull(blockId);
        StoryDocDTO storyDocDTO = storyDocQueryService.getDocument(storyDocId);
        assertNotNull(storyDocDTO.getBlocks());
        assertEquals(1, storyDocDTO.getBlocks().size());

        BlockDTO blockDTO = storyDocDTO.getBlocks().get(0);
        assertNotNull(blockDTO.getBlockId());
        assertEquals(blockId, blockDTO.getBlockId());

        workspaceTestUtils.logFolderStructure("after add block ");
        workspaceTestUtils.logResourceContent("storydoc",storyDocQueryService.getDocument(storyDocId).getUrn());

    }

    @Test
    public void removeBlock() {
        // given
        StoryDocId storyDocId = storyDocService.createDocument();

        BlockId blockId1 = storyDocService.addArtifactBlock(storyDocId);
        BlockId blockId2 = storyDocService.addArtifactBlock(storyDocId);

        {
            StoryDocDTO storyDocDTO = storyDocQueryService.getDocument(storyDocId);
            assertEquals(2, storyDocDTO.getBlocks().size());
        }

        // when
        storyDocService.removeBlock(storyDocId, blockId1);

        // then
        StoryDocDTO storyDocDTO = storyDocQueryService.getDocument(storyDocId);
        assertEquals(1, storyDocDTO.getBlocks().size());

        BlockDTO blockDTO = storyDocDTO.getBlocks().get(0);
        assertEquals(blockId2, blockDTO.getBlockId());

        workspaceTestUtils.logFolderStructure("after remove block ");
        workspaceTestUtils.logResourceContent("storydoc", storyDocQueryService.getDocument(storyDocId).getUrn());

    }

    @Test
    public void addSection() {
        // given
        StoryDocId storyDocId = storyDocService.createDocument();
        // when
        SectionId sectionId = storyDocService.addSection(storyDocId);

        // then
        StoryDocDTO storyDocDTO = storyDocQueryService.getDocument(storyDocId);
        assertEquals(1, storyDocDTO.getBlocks().size());
        assertEquals("SECTION", storyDocDTO.getBlocks().get(0).blockType);

        workspaceTestUtils.logFolderStructure("after add section");
        workspaceTestUtils.logResourceContent("storydoc", storyDocQueryService.getDocument(storyDocId).getUrn());

    }

    @Test
    public void addBlockToSection() {
        // given
        StoryDocId storyDocId = storyDocService.createDocument();
        SectionId sectionId = storyDocService.addSection(storyDocId);

        // when
        BlockId blockId = storyDocService.addArtifactBlock(storyDocId, sectionId);

        // then
        StoryDocDTO storyDocDTO = storyDocQueryService.getDocument(storyDocId);
        assertEquals(2, storyDocDTO.getBlocks().size());

        {
            BlockDTO blockDTO1 = storyDocDTO.getBlocks().get(0);
            assertEquals("SECTION", blockDTO1.blockType);
            assertEquals(sectionId.getId(), blockDTO1.getBlockId().getId());
            assertEquals(null, blockDTO1.getParentBlockId());
        }
        {
            BlockDTO blockDTO2 = storyDocDTO.getBlocks().get(1);
            assertEquals("ARTIFACT", blockDTO2.blockType);
            assertEquals(blockId.getId(), blockDTO2.getBlockId().getId());
            assertEquals(sectionId.getId(), blockDTO2.getParentBlockId().getId());
        }
        workspaceTestUtils.logFolderStructure("after add block to section");
        workspaceTestUtils.logResourceContent("storydoc", storyDocQueryService.getDocument(storyDocId).getUrn());

    }

    @Test
    public void addTag() {
        // given
        StoryDocId storyDocId = storyDocService.createDocument();
        BlockId blockId = storyDocService.addArtifactBlock(storyDocId);

        // when
        storyDocService.addTag(storyDocId, blockId, "TAG");

        // then
        workspaceTestUtils.logFolderStructure("after add tag to block");
        workspaceTestUtils.logResourceContent("storydoc", storyDocQueryService.getDocument(storyDocId).getUrn());

    }


}
