package io.storydoc.server.storydoc.app;

import io.storydoc.server.TestBase;
import io.storydoc.server.storydoc.StoryDocTestFixture;
import io.storydoc.server.storydoc.app.dto.*;
import io.storydoc.server.storydoc.domain.*;
import io.storydoc.server.workspace.WorkspaceTestFixture;
import io.storydoc.server.workspace.app.WorkspaceQueryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

@Slf4j
public class StoryDocServiceTest extends TestBase {

    @Autowired
    private StoryDocService storyDocService;

    @Autowired
    private StoryDocQueryService storyDocQueryService;

    @Autowired
    private WorkspaceQueryService workspaceQueryService;


    private WorkspaceTestFixture workspaceTestFixture;

    @Autowired
    private StoryDocTestFixture storyDocTestFixture;

    @Before
    public void setup() {
        workspaceTestFixture = new WorkspaceTestFixture(workspaceQueryService);
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

        workspaceTestFixture.logFolderStructure("after creating document ");
        workspaceTestFixture.logResourceContent("storydoc", storyDocQueryService.getDocument(storyDocId).getUrn());


    }

    @Test
    public void removeDocument() {
        // given a storydoc
        BlockCoordinate blockCoordinate = storyDocTestFixture.create_storydoc_with_artifact_block();
        StoryDocId storyDocId = blockCoordinate.getStoryDocId();

        workspaceTestFixture.logFolderStructure("before remove ");

        // when I remove the document
        storyDocService.removeDocument(storyDocId);

        workspaceTestFixture.logFolderStructure("after remove ");

        // then it is removed from the document list
        assertEquals(0, storyDocQueryService.getStoryDocs().size());
        // and it cannot be found by id
        assertNull(storyDocQueryService.getDocument(storyDocId));
        assertNull(storyDocQueryService.getStoryDocSummary(storyDocId));


    }

    @Test
    public void renameDocument() {
        // given a storydoc
        BlockCoordinate blockCoordinate = storyDocTestFixture.create_storydoc_with_artifact_block();
        StoryDocId storyDocId = blockCoordinate.getStoryDocId();

        // when I rename the document
        String new_name = "new_name";
        storyDocService.renameDocument(storyDocId, new_name);

        // then the document has been renamed
        assertEquals(new_name, storyDocQueryService.getStoryDocSummary(storyDocId).getName());
        assertEquals(new_name, storyDocQueryService.getDocument(storyDocId).getTitle());
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

        workspaceTestFixture.logFolderStructure("after add block ");
        workspaceTestFixture.logResourceContent("storydoc", storyDocQueryService.getDocument(storyDocId).getUrn());

    }

    @Test
    public void renameArtifactBlock() {
        // given a storydoc with an artifact block
        BlockCoordinate blockCoordinate = storyDocTestFixture.create_storydoc_with_artifact_block();

        // when I rename the artifact block
        String new_name = "new_name";
        storyDocService.renameBlock(blockCoordinate, new_name);

        // then the document has been renamed
        assertEquals(new_name, storyDocQueryService.getDocument(blockCoordinate.getStoryDocId()).getBlocks().get(0).getTitle());
    }

    @Test
    public void moveBlock() {
        // given a storydoc with 3  blocks block_0, block_1, block_2
        StoryDocId storyDocId = storyDocTestFixture.create_storydoc();
        List<BlockId> blockIds = IntStream.range(0, 3)
                .mapToObj(i ->  storyDocTestFixture.add_artifact_block(storyDocId).getBlockId())
                .collect(Collectors.toList());

        // when I move block_2 to index 0
        BlockCoordinate blockToMove = BlockCoordinate.of(storyDocId, blockIds.get(2));
        BlockCoordinate parentBlock = BlockCoordinate.of(storyDocId, storyDocId.asBlockId());

        workspaceTestFixture.logResourceContent("before move", storyDocQueryService.getDocument(storyDocId).getUrn());
        storyDocService.moveBlock(blockToMove, parentBlock, 0);
        workspaceTestFixture.logResourceContent("after move", storyDocQueryService.getDocument(storyDocId).getUrn());

        // then block_2 has index 0, block_0 index 1, block_1 index 2
        StoryDocDTO storyDocDTO = storyDocQueryService.getDocument(storyDocId);
        assertEquals(blockIds.get(2), storyDocDTO.getBlocks().get(0).getBlockId());
        assertEquals(blockIds.get(0), storyDocDTO.getBlocks().get(1).getBlockId());
        assertEquals(blockIds.get(1), storyDocDTO.getBlocks().get(2).getBlockId());

    }

    @Test
    public void add_artifact_to_artifact_block() {
        // given a story with an artifact block
        BlockCoordinate blockCoordinate = storyDocTestFixture.create_storydoc_with_artifact_block();

        // when I add an artifact in the block
        ArtifactCoordinate artifactCoordinate = storyDocTestFixture.add_artifact(blockCoordinate);

        // Then the artifact is in the created state
        StoryDocDTO storyDocDTO  = storyDocQueryService.getDocument(blockCoordinate.getStoryDocId());
        ArtifactDTO artifactDTO = storyDocDTO.getBlocks().get(0).getArtifacts().get(0);
        assertEquals(ArtifactState.CREATED, artifactDTO.getState());

    }

    @Test
    public void addBinaryCollectionToArtifactBlock() {
        // given a story with an artifact block
        String story_name = "story";
        StoryDocId storyDocId = storyDocService.createDocument(story_name);

        String block_name = "block";
        BlockId blockId = storyDocService.addArtifactBlock(storyDocId, block_name);

        BlockCoordinate coordinate = BlockCoordinate.builder().storyDocId(storyDocId).blockId(blockId).build();
        String artifact_name = "artifact";

        // when I add a binary collection artifact
        String binary_type = "binary_type";
        ArtifactId artifactId = ArtifactId.fromString("art-id");
        storyDocService.createBinaryCollectionArtifact(coordinate, artifactId , "art_type", binary_type, artifact_name);

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

        workspaceTestFixture.logFolderStructure("after add block ");
        workspaceTestFixture.logResourceContent("storydoc", storyDocQueryService.getDocument(storyDocId).getUrn());

    }

    @Test
    public void change_artifact_state() {
        // given a story with an artifact block
        BlockCoordinate blockCoordinate = storyDocTestFixture.create_storydoc_with_artifact_block();

        // given an artifact
        ArtifactCoordinate artifactCoordinate = storyDocTestFixture.add_artifact(blockCoordinate);
        StoryDocDTO storyDocDTOBefore  = storyDocQueryService.getDocument(blockCoordinate.getStoryDocId());

        // When I change the state of the artifact
        ArtifactState newState = ArtifactState.READY;
        assertNotEquals(newState, storyDocDTOBefore.getBlocks().get(0).getArtifacts().get(0).getState());
        storyDocService.changeArtifactState(artifactCoordinate, newState);

        // then the artifact has the new state
        StoryDocDTO storyDocDTOAfter  = storyDocQueryService.getDocument(blockCoordinate.getStoryDocId());
        assertEquals(newState, storyDocDTOAfter.getBlocks().get(0).getArtifacts().get(0).getState());

    }

    @Test
    public void removeArtifact() {
        // given a story with an artifact block
        String story_name = "story";
        StoryDocId storyDocId = storyDocService.createDocument(story_name);

        String block_name = "block";
        BlockId blockId = storyDocService.addArtifactBlock(storyDocId, block_name);

        BlockCoordinate blockCoordinate = BlockCoordinate.of(storyDocId, blockId);
        String artifact_name = "artifact";

        // given a binary collection artifact
        String binary_type = "binary_type";
        ArtifactId artifactId = ArtifactId.fromString("art-id");
        storyDocService.createBinaryCollectionArtifact(blockCoordinate,artifactId , "art_type", binary_type, artifact_name);
        ArtifactCoordinate artifactCoordinate = ArtifactCoordinate.of(blockCoordinate, artifactId);

        // when I remove the collection artifact
        workspaceTestFixture.logFolderStructure("before remove artifact ");
        storyDocService.removeArtifact(artifactCoordinate);
        workspaceTestFixture.logFolderStructure("after remove artifact ");

        // then I can no longer find it
        StoryDocDTO storyDocDTO = storyDocQueryService.getDocument(storyDocId);
        assertEquals(0, storyDocDTO.getBlocks().get(0).getArtifacts().size());
        BlockDTO blockDTO = storyDocDTO.getBlocks().get(0);
        // and it's folders/resources are removed
        // todo
    }

    @Test
    public void addResourceToBinaryCollection() {

        // given an a binary collection artifact
        String story_name = "story";
        StoryDocId storyDocId = storyDocService.createDocument(story_name);

        String block_name = "block";
        BlockId blockId = storyDocService.addArtifactBlock(storyDocId, block_name);

        BlockCoordinate coordinate = BlockCoordinate.builder().storyDocId(storyDocId).blockId(blockId).build();

        String artifact_name = "artifact";
        ArtifactId artifactId = ArtifactId.fromString("art-id");
        storyDocService.createBinaryCollectionArtifact(coordinate, artifactId , "art_type", "binary_type", artifact_name);

        // when I upload a binary resource into the collection
        InputStream inputStream = this.getClass().getResourceAsStream("dummy-image-10x10.png");
        String item_name = "item-01";
        storyDocService.addItemToBinaryCollection(coordinate, artifactId, item_name, inputStream);

        workspaceTestFixture.logFolderStructure("after add binary resource ");
        workspaceTestFixture.logResourceContent("storydoc", storyDocQueryService.getDocument(storyDocId).getUrn());

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

        workspaceTestFixture.logFolderStructure("before remove block ");

        // when
        storyDocService.removeBlock(BlockCoordinate.of(storyDocId, blockId1));

        // then
        StoryDocDTO storyDocDTO = storyDocQueryService.getDocument(storyDocId);
        assertEquals(1, storyDocDTO.getBlocks().size());

        BlockDTO blockDTO = storyDocDTO.getBlocks().get(0);
        assertEquals(blockId2, blockDTO.getBlockId());

        workspaceTestFixture.logFolderStructure("after remove block ");
        workspaceTestFixture.logResourceContent("storydoc", storyDocQueryService.getDocument(storyDocId).getUrn());

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

        workspaceTestFixture.logFolderStructure("after add section");
        workspaceTestFixture.logResourceContent("storydoc", storyDocQueryService.getDocument(storyDocId).getUrn());

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
        workspaceTestFixture.logFolderStructure("after add block to section");
        workspaceTestFixture.logResourceContent("storydoc", storyDocQueryService.getDocument(storyDocId).getUrn());

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
        workspaceTestFixture.logFolderStructure("after add tag to block");
        workspaceTestFixture.logResourceContent("storydoc", storyDocQueryService.getDocument(storyDocId).getUrn());

    }

    @Test
    public void association() {
        // given a storydoc with an artifact block
        BlockCoordinate blockCoordinate = storyDocTestFixture.create_storydoc_with_artifact_block();
        StoryDocId storyDocId = blockCoordinate.getStoryDocId();

        // given 2 artifacts
        ArtifactCoordinate artifactCoordinate1 = storyDocTestFixture.add_artifact(blockCoordinate, "type-01");
        ArtifactCoordinate artifactCoordinate2 = storyDocTestFixture.add_artifact(blockCoordinate, "type-02");

        // when I add an association
        String typeName = "association-type";
        storyDocService.addAssociation(artifactCoordinate1, artifactCoordinate2, typeName);

        // then the first artifac thas an outgoing association
        List<AssociationDto> associationDtos = storyDocQueryService.getAssociationsFrom(artifactCoordinate1, typeName);
        assertNotNull(associationDtos);
        assertEquals(1, associationDtos.size());
        assertEquals(artifactCoordinate1, associationDtos.get(0).getFrom());
        assertEquals(artifactCoordinate2, associationDtos.get(0).getTo());
    }


}
