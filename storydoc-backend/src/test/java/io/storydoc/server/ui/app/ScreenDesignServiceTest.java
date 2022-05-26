package io.storydoc.server.ui.app;

import io.storydoc.server.TestBase;
import io.storydoc.server.storydoc.StoryDocTestFixture;
import io.storydoc.server.storydoc.app.StoryDocQueryService;
import io.storydoc.server.storydoc.app.dto.BlockDTO;
import io.storydoc.server.storydoc.app.dto.StoryDocDTO;
import io.storydoc.server.storydoc.domain.BlockCoordinate;
import io.storydoc.server.storydoc.domain.StoryDocId;
import io.storydoc.server.ui.app.screendesign.*;
import io.storydoc.server.ui.domain.screendesign.SDComponentId;
import io.storydoc.server.ui.domain.screendesign.ScreenDesignCoordinate;
import io.storydoc.server.workspace.WorkspaceTestFixture;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class ScreenDesignServiceTest extends TestBase {

    @Autowired
    private StoryDocTestFixture storyDocTestFixture;

    @Autowired
    private StoryDocQueryService storyDocQueryService;

    @Autowired
    private ScreenDesignService screenDesignService;

    @Autowired
    private ScreenDesignQueryService screenDesignQueryService;

    @Autowired
    private WorkspaceTestFixture workspaceTestFixture;

    @Test
    public void create_screen_design() {
        // given a storydoc with a artifact block
        BlockCoordinate blockCoordinate = storyDocTestFixture.create_storydoc_with_artifact_block();
        StoryDocId storyDocId = blockCoordinate.getStoryDocId();

        // when I create a screen design artifact
        String name = "screendesign artifact";
        ScreenDesignCoordinate screenDesignCoordinate = screenDesignService.createScreenDesign(blockCoordinate, name);

        workspaceTestFixture.logFolderStructure("after uploading screenshot");
        workspaceTestFixture.logResourceContent("storydoc", storyDocQueryService.getDocument(blockCoordinate.getStoryDocId()).getUrn());
        workspaceTestFixture.logResourceContent("screendesign", storyDocQueryService.getArtifactUrn(screenDesignCoordinate.asArtifactCoordinate()));

        // then the artifact is added to the storydoc
        StoryDocDTO storyDocDTO = storyDocQueryService.getDocument(storyDocId);

        BlockDTO blockDTO = storyDocDTO.getBlocks().get(0);
        assertNotNull(blockDTO.getArtifacts());
        assertEquals(1, blockDTO.getArtifacts().size());

        // and I can query for the design document
        ScreenDesignDTO dto = screenDesignQueryService.getScreenDesign(screenDesignCoordinate);
        assertNotNull(dto);
        assertEquals(screenDesignCoordinate, dto.getCoordinate());
        assertNotNull(dto.getStoryDocSummaryDTO());
        assertEquals(blockCoordinate.getStoryDocId(), dto.getStoryDocSummaryDTO().getStoryDocId());
        assertEquals(name, dto.getName());
        assertNotNull(dto.getRootContainer());
        assertNotNull(dto.getRootContainer().getChildren());
        assertNotNull(dto.getRootContainer().getId());

    }

    @Test
    public void add_component_to_container() {
        // given a storydoc with a artifact block
        BlockCoordinate blockCoordinate = storyDocTestFixture.create_storydoc_with_artifact_block();
        StoryDocId storyDocId = blockCoordinate.getStoryDocId();

        // given a screen design artifact
        String name = "screendesign artifact";
        ScreenDesignCoordinate screenDesignCoordinate = screenDesignService.createScreenDesign(blockCoordinate, name);
        SDComponentId rootContainerId = screenDesignQueryService.getScreenDesign(screenDesignCoordinate).getRootContainer().getId();

        // when I add a component to the container
        String type = "BUTTON";
        SDComponentId componentId = screenDesignService.addComponent(screenDesignCoordinate, rootContainerId, type, 0, 0);

        workspaceTestFixture.logResourceContent("screendesign after add", storyDocQueryService.getArtifactUrn(screenDesignCoordinate.asArtifactCoordinate()));

        // Then the component belongs to the container
        ScreenDesignDTO dto = screenDesignQueryService.getScreenDesign(screenDesignCoordinate);
        assertEquals(1, dto.getRootContainer().getChildren().size());

        SDComponentTypeSelectionDTO selectionDTO = dto.getRootContainer().getChildren().get(0);
        assertNull(selectionDTO.getContainer());
        assertNotNull(selectionDTO.getComponent());

        SDComponentDTO componentDTO = selectionDTO.getComponent();
        assertNotNull(componentDTO.getId());
        assertEquals(type, componentDTO.getType());

    }

    @Test
    public void component_palette() {
        // When I get the component palette
        List<ComponentDescriptorDTO> palette = screenDesignQueryService.getComponentPalette();

        // Then the "BUTTON" is present
        ComponentDescriptorDTO buttonDescriptor = palette.stream()
                .filter(desc -> desc.getType().equals("BUTTON"))
                .findFirst()
                .orElse(null);
        assertNotNull(buttonDescriptor);

        // and button descriptor has button attribute descriptors
    }


}