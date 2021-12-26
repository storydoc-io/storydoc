package io.storydoc.server.document.app;

import io.storydoc.server.TestBase;
import io.storydoc.server.document.app.dto.BlockDTO;
import io.storydoc.server.document.app.dto.StoryDocDTO;
import io.storydoc.server.document.domain.BlockId;
import io.storydoc.server.document.domain.SectionId;
import io.storydoc.server.document.domain.StoryDocId;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Slf4j
public class DocumentServiceTest extends TestBase {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private DocumentQueryService documentQueryService;

    @Test
    public void createDocument() {

        StoryDocId storyDocId = documentService.createDocument();

        assertNotNull(storyDocId);

        StoryDocDTO dto = documentQueryService.getDocument(storyDocId);

        assertNotNull(dto);
        assertEquals(storyDocId, dto.getStoryDocId());
        assertNotNull(dto.getBlocks());

    }

    @Test
    public void addBlock() {
        // given
        StoryDocId storyDocId = documentService.createDocument();

        // when
        BlockId blockId = documentService.addArtifactBlock(storyDocId);

        // then
        assertNotNull(blockId);
        StoryDocDTO storyDocDTO = documentQueryService.getDocument(storyDocId);
        assertNotNull(storyDocDTO.getBlocks());
        assertEquals(1, storyDocDTO.getBlocks().size());

        BlockDTO blockDTO = storyDocDTO.getBlocks().get(0);
        assertNotNull(blockDTO.getBlockId());
        assertEquals(blockId, blockDTO.getBlockId());

    }

    @Test
    public void removeBlock() {
        // given
        StoryDocId storyDocId = documentService.createDocument();

        BlockId blockId1 = documentService.addArtifactBlock(storyDocId);
        BlockId blockId2 = documentService.addArtifactBlock(storyDocId);

        {
            dump("before remove", storyDocId);
            StoryDocDTO storyDocDTO = documentQueryService.getDocument(storyDocId);
            assertEquals(2, storyDocDTO.getBlocks().size());
        }

        // when
        documentService.removeBlock(storyDocId, blockId1);

        // then
        dump("after remove", storyDocId);
        StoryDocDTO storyDocDTO = documentQueryService.getDocument(storyDocId);
        assertEquals(1, storyDocDTO.getBlocks().size());

        BlockDTO blockDTO = storyDocDTO.getBlocks().get(0);
        assertEquals(blockId2, blockDTO.getBlockId());

    }

    @Test
    public void addSection() {
        // given
        StoryDocId storyDocId = documentService.createDocument();
        // when
        SectionId sectionId = documentService.addSection(storyDocId);

        // then
        dump("doc", storyDocId);

        StoryDocDTO storyDocDTO = documentQueryService.getDocument(storyDocId);
        assertEquals(1, storyDocDTO.getBlocks().size());
        assertEquals("SECTION", storyDocDTO.getBlocks().get(0).blockType);
    }

    @Test
    public void addBlockToSection() {
        // given
        StoryDocId storyDocId = documentService.createDocument();
        SectionId sectionId = documentService.addSection(storyDocId);

        // when
        BlockId blockId = documentService.addArtifactBlock(storyDocId, sectionId);

        // then
        dump("document with section with block", storyDocId);

        StoryDocDTO storyDocDTO = documentQueryService.getDocument(storyDocId);
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

    }

    @Test
    public void addTag() {
        // given
        StoryDocId storyDocId = documentService.createDocument();
        BlockId blockId = documentService.addArtifactBlock(storyDocId);

        // when
        documentService.addTag(storyDocId, blockId, "TAG");

        // then
        dump("with tag", storyDocId);


    }




}
