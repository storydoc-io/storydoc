package io.storydoc.server.storydoc.app;

import io.storydoc.server.TestBase;
import io.storydoc.server.storydoc.app.dto.ArtifactDTO;
import io.storydoc.server.storydoc.app.dto.BlockDTO;
import io.storydoc.server.storydoc.app.dto.ItemDTO;
import io.storydoc.server.storydoc.app.dto.StoryDocDTO;
import io.storydoc.server.storydoc.domain.*;
import io.storydoc.server.workspace.WorkspaceTestUtils;
import io.storydoc.server.workspace.app.WorkspaceQueryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;

import static org.junit.Assert.*;

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
        String story_name = "story";
        StoryDocId storyDocId = storyDocService.createDocument(story_name);

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
        String story_name = "story";
        StoryDocId storyDocId = storyDocService.createDocument(story_name);

        // when
        String block_name = "block";
        BlockId blockId = storyDocService.addArtifactBlock(storyDocId, block_name);

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
    public void addBinaryCollectionToArtifactBlock() {
        // given a story with an artifact block
        String story_name = "story";
        StoryDocId storyDocId = storyDocService.createDocument(story_name);

        String block_name = "block";
        BlockId blockId = storyDocService.addArtifactBlock(storyDocId, block_name);

        ArtifactBlockCoordinate coordinate = ArtifactBlockCoordinate.builder().storyDocId(storyDocId).blockId(blockId).build();
        String artifact_name = "artifact";

        // when I add a binary collection artifact
        String binary_type = "binary_type";
        ArtifactId artifactId = storyDocService.createBinaryCollectionArtifact(coordinate, "art_type", binary_type, artifact_name);

        // then there is a artifact added to the block
        assertNotNull(blockId);
        StoryDocDTO storyDocDTO = storyDocQueryService.getDocument(storyDocId);
        BlockDTO blockDTO = storyDocDTO.getBlocks().get(0);
        assertEquals(blockId, blockDTO.getBlockId());
        assertEquals(1, blockDTO.getArtifacts().size());

        // and then I can find that the artifact is binary and a collection , and the type of binary items of the collection
        ArtifactDTO artifactDTO = blockDTO.getArtifacts().get(0);
        assertNotNull(artifactDTO);
        assertTrue(artifactDTO.isBinary());
        assertTrue(artifactDTO.isCollection());
        assertEquals(binary_type, artifactDTO.getBinaryType());
        assertNotNull(artifactDTO.getItems());

        // and then the list of items in the collection is empty
        assertEquals(0, artifactDTO.getItems().size());

        workspaceTestUtils.logFolderStructure("after add block ");
        workspaceTestUtils.logResourceContent("storydoc",storyDocQueryService.getDocument(storyDocId).getUrn());

    }

    @Test
    public void addResourceToBinaryCollection() {

        // given an a binary collection artifact
        String story_name = "story";
        StoryDocId storyDocId = storyDocService.createDocument(story_name);

        String block_name = "block";
        BlockId blockId = storyDocService.addArtifactBlock(storyDocId, block_name);

        ArtifactBlockCoordinate coordinate = ArtifactBlockCoordinate.builder().storyDocId(storyDocId).blockId(blockId).build();

        String artifact_name = "artifact";
        ArtifactId artifactId = storyDocService.createBinaryCollectionArtifact(coordinate, "art_type", "binary_type", artifact_name);

        // when I upload a binary resource into the collection
        InputStream inputStream = this.getClass().getResourceAsStream("dummy-image-10x10.png");
        String item_name = "item-01";
        storyDocService.addItemToBinaryCollection(coordinate, artifactId, item_name, inputStream);

        workspaceTestUtils.logFolderStructure("after add binary resource ");
        workspaceTestUtils.logResourceContent("storydoc",storyDocQueryService.getDocument(storyDocId).getUrn());

        // then the resource is added to the list of items in the collection
        StoryDocDTO storyDocDTO = storyDocQueryService.getDocument(storyDocId);
        BlockDTO blockDTO = storyDocDTO.getBlocks().get(0);
        ArtifactDTO artifactDTO = blockDTO.getArtifacts().get(0);
        assertEquals(1, artifactDTO.getItems().size());
        ItemDTO itemDTO = artifactDTO.getItems().get(0);
        assertNotNull(itemDTO);
        assertEquals(item_name, itemDTO.getName());
        assertNotNull(itemDTO.getId());


    }



    @Test
    public void removeBlock() {
        // given
        String story_name = "story";
        StoryDocId storyDocId = storyDocService.createDocument(story_name);

        String block_name_1 = "block-1";
        BlockId blockId1 = storyDocService.addArtifactBlock(storyDocId, block_name_1);
        String block_name_2 = "block-2";
        BlockId blockId2 = storyDocService.addArtifactBlock(storyDocId, block_name_2);

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
        String story_name = "story";
        StoryDocId storyDocId = storyDocService.createDocument(story_name);
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
        String story_name = "story";
        StoryDocId storyDocId = storyDocService.createDocument(story_name);
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
        String story_name = "story";
        StoryDocId storyDocId = storyDocService.createDocument(story_name);

        String block_name = "block";
        BlockId blockId = storyDocService.addArtifactBlock(storyDocId, block_name);

        // when
        storyDocService.addTag(storyDocId, blockId, "TAG");

        // then
        workspaceTestUtils.logFolderStructure("after add tag to block");
        workspaceTestUtils.logResourceContent("storydoc", storyDocQueryService.getDocument(storyDocId).getUrn());

    }


}
