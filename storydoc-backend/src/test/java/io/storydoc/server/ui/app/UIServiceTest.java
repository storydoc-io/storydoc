package io.storydoc.server.ui.app;

import io.storydoc.server.TestBase;
import io.storydoc.server.storydoc.StoryDocPageStub;
import io.storydoc.server.storydoc.app.StoryDocQueryService;
import io.storydoc.server.storydoc.app.StoryDocService;
import io.storydoc.server.storydoc.app.dto.ArtifactDTO;
import io.storydoc.server.storydoc.app.dto.BlockDTO;
import io.storydoc.server.storydoc.app.dto.StoryDocDTO;
import io.storydoc.server.storydoc.domain.ArtifactBlockCoordinate;
import io.storydoc.server.storydoc.domain.ArtifactId;
import io.storydoc.server.storydoc.domain.BlockId;
import io.storydoc.server.storydoc.domain.StoryDocId;
import io.storydoc.server.ui.UIPageStub;
import io.storydoc.server.ui.domain.*;
import io.storydoc.server.workspace.WorkspaceTestUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UIServiceTest extends TestBase {

    @Autowired
    private StoryDocService storyDocService;

    @Autowired
    private StoryDocQueryService storyDocQueryService;

    @Autowired
    private UIService uiService;

    @Autowired
    private UIStorage uiStorage;

    @Autowired
    private UIQueryService uiQueryService;

    @Autowired
    private WorkspaceTestUtils workspaceTestUtils;

    @Autowired
    UIPageStub uiPage;

    @Autowired
    StoryDocPageStub storyDocPage;

    @Test
    public void createMockUI() {
        // given a storydoc with a artifact block
        StoryDocId storyDocId = storyDocService.createDocument();
        BlockId blockId = storyDocService.addArtifactBlock(storyDocId);
        ArtifactBlockCoordinate coordinate = ArtifactBlockCoordinate.builder()
                .storyDocId(storyDocId)
                .blockId(blockId)
                .build();

        // when I create a mockui artifact
        String mockui_artifact_name = "mockui artifact";
        MockUIId uiId = uiService.createMockUI(coordinate, mockui_artifact_name);
        assertNotNull(uiId);

        workspaceTestUtils.logFolderStructure("after creating mock ui artifact ");
        workspaceTestUtils.logResourceContent("storydoc", storyDocQueryService.getDocument(storyDocId).getUrn());
        workspaceTestUtils.logResourceContent("mock ui ", uiStorage.getUIArtifactResourceUrn(coordinate, uiId));

        // then the artifact is added to the storydoc
        StoryDocDTO storyDocDTO = storyDocQueryService.getDocument(storyDocId);

        BlockDTO blockDTO = storyDocDTO.getBlocks().get(0);
        assertNotNull(blockDTO.getArtifacts());
        assertEquals(1, blockDTO.getArtifacts().size());

        ArtifactDTO artifactDTO = blockDTO.getArtifacts().get(0);
        assertNotNull(artifactDTO);
        assertEquals(uiId.getId(), artifactDTO.getArtifactId().getId());
        assertEquals(MockUI.ARTIFACT_TYPE, artifactDTO.getArtifactType());

        // and then I can find  mockui artifact
        MockUIDTO dto = uiQueryService.getMockUIDTO(coordinate, uiId);
        assertNotNull(dto);
        assertEquals(mockui_artifact_name, dto.getName());
        assertEquals(dto.getId(), uiId);

    }

    @Test
    public void uploadScreenshot() throws IOException {

        // given a storydoc with a artifact block
        StoryDocId storyDocId = storyDocService.createDocument();
        BlockId blockId = storyDocService.addArtifactBlock(storyDocId);
        ArtifactBlockCoordinate coordinate = ArtifactBlockCoordinate.builder()
                .storyDocId(storyDocId)
                .blockId(blockId)
                .build();
        // given an inputstream of a screenshot
        InputStream inputStream = this.getClass().getResourceAsStream("dummy-image-10x10.png");

        // when I upload the screenshot
        ScreenshotId screenshotId = uiService.uploadScreenShot(coordinate, inputStream, "screenshot");

        workspaceTestUtils.logFolderStructure("after uploading screenshot ");
        workspaceTestUtils.logResourceContent("storydoc", storyDocQueryService.getDocument(storyDocId).getUrn());
        workspaceTestUtils.logBinaryResourceContent("screenshot", uiStorage.getScreenshotArtifactResourceUrn(coordinate, screenshotId));

        //
    }

    @Test
    public void uploadScreenshotsAndAddToMockUI() throws IOException {

        // given a storydoc was created with an artifact block
        StoryDocId storyDocId = storyDocService.createDocument();
        BlockId blockId = storyDocService.addArtifactBlock(storyDocId);

        ArtifactBlockCoordinate coordinate = ArtifactBlockCoordinate.builder()
                .storyDocId(storyDocId)
                .blockId(blockId)
                .build();

        // given a mockui artifact was created in that block
        MockUIId mockUIId = uiService.createMockUI(coordinate, "mockui");

        // when I upload 5 images
        for (int screenshotCount=1; screenshotCount <= 5; screenshotCount++) {

            // given an inputstream of an image
            InputStream inputStream = this.getClass().getResourceAsStream("dummy-image-10x10.png");

            // when I upload the image
            String screenshotName = "screenshot-" + screenshotCount;
            ScreenshotId screenshotId = uiService.uploadScreenShot(coordinate, inputStream, screenshotName);

            workspaceTestUtils.logFolderStructure("after uploading screenshot #" + screenshotCount);
            workspaceTestUtils.logResourceContent("storydoc after uploading screenshot #" + screenshotCount, storyDocQueryService.getDocument(storyDocId).getUrn());

            // then I can find it
            List<ScreenshotId> screenshotIds = uiQueryService.getScreenshots(coordinate);
            assertNotNull(screenshotIds);
            assertEquals(screenshotCount, screenshotIds.size());
            assertEquals(screenshotId, screenshotIds.get(screenshotCount-1));

            // TODO then I can download it
        }

        // when I add the screenshots to the mockui
        for (int screenshotIdx=0; screenshotIdx < 5; screenshotIdx++){
            ArtifactId artifactId = storyDocQueryService.getArtifactsByType(coordinate, Screenshot.ARTIFACT_TYPE).get(screenshotIdx);
            ScreenshotId screenshotId = new ScreenshotId(artifactId.getId());
            uiService.addScreenShot(coordinate, mockUIId, screenshotId);
        }

        workspaceTestUtils.logResourceContent("storydoc after adding screenshot ", storyDocQueryService.getDocument(storyDocId).getUrn());


        // then the screenshots are part of the mockui artifact
        MockUIDTO mockUIDTO  = uiQueryService.getMockUIDTO(coordinate, mockUIId);
        assertNotNull(mockUIDTO);
        assertEquals(5, mockUIDTO.getScreenshots().size());
        workspaceTestUtils.logResourceContent("mock ui after adding screenshots", uiStorage.getUIArtifactResourceUrn(coordinate, mockUIId));

    }

}