package io.storydoc.server.ui.app;

import io.storydoc.server.TestBase;
import io.storydoc.server.storydoc.StoryDocPageStub;
import io.storydoc.server.storydoc.StoryDocTestUtils;
import io.storydoc.server.storydoc.app.StoryDocQueryService;
import io.storydoc.server.storydoc.app.StoryDocService;
import io.storydoc.server.storydoc.app.dto.ArtifactDTO;
import io.storydoc.server.storydoc.app.dto.BlockDTO;
import io.storydoc.server.storydoc.app.dto.StoryDocDTO;
import io.storydoc.server.storydoc.domain.ArtifactBlockCoordinate;
import io.storydoc.server.storydoc.domain.BlockId;
import io.storydoc.server.storydoc.domain.StoryDocId;
import io.storydoc.server.timeline.app.TimeLineTestUtils;
import io.storydoc.server.timeline.domain.TimeLineCoordinate;
import io.storydoc.server.timeline.domain.TimeLineItemId;
import io.storydoc.server.timeline.domain.TimeLineModelCoordinate;
import io.storydoc.server.ui.UIPageStub;
import io.storydoc.server.ui.domain.*;
import io.storydoc.server.workspace.WorkspaceTestUtils;
import io.storydoc.server.workspace.domain.WorkspaceException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
    private UITestUtils uiTestUtils;

    @Autowired
    UIPageStub uiPage;

    @Autowired
    StoryDocPageStub storyDocPage;
    private ScreenshotCoordinate screenshotCoordinate;

    @Test
    public void createMockUI() {
        // given a storydoc with a artifact block
        String story_name = "story";
        StoryDocId storyDocId = storyDocService.createDocument(story_name);

        String block_name = "block";
        BlockId blockId = storyDocService.addArtifactBlock(storyDocId, block_name);
        ArtifactBlockCoordinate coordinate = ArtifactBlockCoordinate.builder()
                .storyDocId(storyDocId)
                .blockId(blockId)
                .build();

        // when I create a uiscenario artifact
        String uiscenario_artifact_name = "uiscenario artifact";
        UIScenarioId uiId = uiService.createUIScenario(coordinate, uiscenario_artifact_name);
        UIScenarioCoordinate scenarioCoordinate = UIScenarioCoordinate.of(coordinate, uiId);
        assertNotNull(uiId);

        workspaceTestUtils.logFolderStructure("after creating mock ui artifact ");
        workspaceTestUtils.logResourceContent("storydoc", storyDocQueryService.getDocument(storyDocId).getUrn());
        workspaceTestUtils.logResourceContent("mock ui ", uiStorage.getUIScenarioUrn(scenarioCoordinate));

        // then the artifact is added to the storydoc
        StoryDocDTO storyDocDTO = storyDocQueryService.getDocument(storyDocId);

        BlockDTO blockDTO = storyDocDTO.getBlocks().get(0);
        assertNotNull(blockDTO.getArtifacts());
        assertEquals(1, blockDTO.getArtifacts().size());

        ArtifactDTO artifactDTO = blockDTO.getArtifacts().get(0);
        assertNotNull(artifactDTO);
        assertEquals(uiId.getId(), artifactDTO.getArtifactId().getId());
        assertEquals(UIScenario.ARTIFACT_TYPE, artifactDTO.getArtifactType());

        // and then I can find  uiscenario artifact
        UIScenarioDTO dto = uiQueryService.getUIScenario(scenarioCoordinate);
        assertNotNull(dto);
        assertEquals(uiscenario_artifact_name, dto.getName());
        assertEquals(dto.getId(), uiId);

    }

    @Test
    public void createScreenShotCollection() {
        // given a storydoc with a artifact block
        String story_name = "story";
        StoryDocId storyDocId = storyDocService.createDocument(story_name);

        String block_name = "block";
        BlockId blockId = storyDocService.addArtifactBlock(storyDocId, block_name);
        ArtifactBlockCoordinate coordinate = ArtifactBlockCoordinate.builder()
                .storyDocId(storyDocId)
                .blockId(blockId)
                .build();
        // when I create a ScreenshotCollection artifact
        String screenshots_artifact_name = "screenshots";
        ScreenShotCollectionId id = uiService.createScreenShotCollection(coordinate, screenshots_artifact_name);
        assertNotNull(id);

        workspaceTestUtils.logFolderStructure("after creating mock ui artifact ");
        workspaceTestUtils.logResourceContent("storydoc", storyDocQueryService.getDocument(storyDocId).getUrn());

    }

    @Test
    public void addScreenShotToCollection() throws WorkspaceException {
        // given  a storydoc with an artifact block
        // given a storydoc with an artifact paragraph
        ArtifactBlockCoordinate blockCoordinate = storyDocTestUtils.create_storydoc_with_artifact_block();

        // given a screenshot collection artifact
        String screenshots_artifact_name = "screenshots";
        ScreenShotCollectionId screenShotCollectionId = uiService.createScreenShotCollection(blockCoordinate, screenshots_artifact_name);
        ScreenshotCollectionCoordinate collectionCoordinate = ScreenshotCollectionCoordinate.of(blockCoordinate, screenShotCollectionId);

        // given an inputstream of a screenshot
        InputStream inputStream = this.getClass().getResourceAsStream("dummy-image-10x10.png");

        // when I upload a screenshot to the collection
        String screenshot_name = "screenshot";
        ScreenShotId screenShotId = uiService.uploadScreenShotToCollection(collectionCoordinate, inputStream, screenshot_name);

        workspaceTestUtils.logFolderStructure("after uploading screenshot");
        workspaceTestUtils.logResourceContent("storydoc", storyDocQueryService.getDocument(blockCoordinate.getStoryDocId()).getUrn());

        // then I can find it in the list of screenshots in the collection

        ScreenShotCollectionDTO dto = uiQueryService.getScreenShotCollection(collectionCoordinate);
        assertNotNull(dto);
        assertEquals(1, dto.getScreenShots().size());

        ScreenShotDTO screenShotDTO = dto.getScreenShots().get(0);
        assertEquals(screenshot_name, screenShotDTO.getName());
        assertEquals(screenShotId, screenShotDTO.getId());

        // and then I can download it
        InputStream  inputStream2 = uiQueryService.getScreenshot(collectionCoordinate, screenShotId);
        workspaceTestUtils.logBinaryInputstream(inputStream2);

    }

    @Test
    public void UIScenario_Has_Default_Associated_Snapshot_Collection () {
        // given a storydoc with an artifact paragraph
        ArtifactBlockCoordinate blockCoordinate = storyDocTestUtils.create_storydoc_with_artifact_block();
        // given a screenshot collection artifact in that paragraph
        ScreenshotCollectionCoordinate collectionCoordinate = uiTestUtils.createScreenshotCollection(blockCoordinate);
        // given  a screenshot is uploaded to the collection
        ScreenShotId screenShotId = uiTestUtils.upoadScreenShotToCollection(collectionCoordinate);

        // when I create a UI scenario artifact in the same paragraph
        UIScenarioId uiScenarioId = uiService.createUIScenario(blockCoordinate, "uiscenario");
        UIScenarioCoordinate scenarioCoordinate = UIScenarioCoordinate.of(blockCoordinate, uiScenarioId);

        // then the mock ui is by default associated with this collection  of screenshots
        UIScenarioDTO mockUIDTO = uiQueryService.getUIScenario(scenarioCoordinate);
        List<ScreenshotCollectionSummaryDTO> collections = mockUIDTO.getAssociatedCollections();
        assertNotNull(collections);
        assertEquals(1, collections.size());

        ScreenshotCollectionCoordinate collectionRefDTO = collections.get(0).getCollectionCoordinate();
        assertEquals(collectionCoordinate.getScreenShotCollectionId(), collectionRefDTO.getScreenShotCollectionId());
        assertEquals(blockCoordinate.getStoryDocId(), collectionRefDTO.getBlockCoordinate().getStoryDocId());
        assertEquals(blockCoordinate.getBlockId(), collectionRefDTO.getBlockCoordinate().getBlockId());

        workspaceTestUtils.logFolderStructure("after uploading screenshot");
        workspaceTestUtils.logResourceContent("storydoc", storyDocQueryService.getDocument(blockCoordinate.getStoryDocId()).getUrn());

    }

    @Autowired
    private StoryDocTestUtils storyDocTestUtils;

    @Autowired
    private TimeLineTestUtils timeLineTestUtils;

    @Test
    public void associate_timeline_to_ui_scenario() {

        // given a storydoc with an artifact paragraph
        ArtifactBlockCoordinate blockCoordinate = storyDocTestUtils.create_storydoc_with_artifact_block();

        // given a ui scenario
        UIScenarioCoordinate scenarioCoordinate = uiTestUtils.createUIScenario(blockCoordinate);

        // given a timeline model
        TimeLineModelCoordinate timeLineModelCoordinate = timeLineTestUtils.createTimeLineModel(blockCoordinate);
        TimeLineCoordinate defaultTimeLineCoordinate = timeLineTestUtils.getDefaultTimeLine(timeLineModelCoordinate);

        // when I associate the timeline with the UI scenario
        uiService.setTimeLineForUIScenario(scenarioCoordinate, defaultTimeLineCoordinate);

        // I can find the associated timeline
        UIScenarioDTO dto = uiQueryService.getUIScenario(scenarioCoordinate);
        assertEquals(timeLineModelCoordinate, dto.getTimeLineModelCoordinate());

        workspaceTestUtils.logFolderStructure("after setting timeline");
        workspaceTestUtils.logResourceContent("storydoc", storyDocQueryService.getDocument(blockCoordinate.getStoryDocId()).getUrn());


    }

    @Test
    public void add_screenshot_to_ui_scenario() {

        // given a storydoc with an artifact paragraph
        ArtifactBlockCoordinate blockCoordinate = storyDocTestUtils.create_storydoc_with_artifact_block();

        // given a screenshot collection artifact in that paragraph
        ScreenshotCollectionCoordinate collectionCoordinate = uiTestUtils.createScreenshotCollection(blockCoordinate);

        // given  a screenshot is uploaded to the collection
        ScreenShotId screenShotId = uiTestUtils.upoadScreenShotToCollection(collectionCoordinate);

        // given a uiscenario artifact in the same paragraph
        UIScenarioCoordinate scenarioCoordinate =  uiTestUtils.createUIScenario(blockCoordinate);

        // given a timeline model in the same paragraph
        TimeLineModelCoordinate timeLineModelCoordinate = timeLineTestUtils.createTimeLineModel(blockCoordinate);
        TimeLineCoordinate defaultTimeLineCoordinate = timeLineTestUtils.getDefaultTimeLine(timeLineModelCoordinate);
        // given I the timeline is associated with the UI scenario
        uiService.setTimeLineForUIScenario(scenarioCoordinate, defaultTimeLineCoordinate);

        // given a timeLine item is added to the default timeline
        TimeLineItemId timeLineItemId = timeLineTestUtils.addItemToDefaultTimeLine(timeLineModelCoordinate);

        // when I add the screenshot to the UI scenario

        screenshotCoordinate = ScreenshotCoordinate.of(collectionCoordinate, screenShotId);
        uiService.addScreenShotToUIScenario(scenarioCoordinate, screenshotCoordinate, timeLineItemId);

        // then the UI scenario has the screenshot
        UIScenarioDTO mockUIDTO = uiQueryService.getUIScenario(scenarioCoordinate);

        assertEquals(1, mockUIDTO.getScreenshots().size());
        ScreenShotTimeLineItemDTO screenShotDTO = mockUIDTO.getScreenshots().get(0);
        assertEquals(screenshotCoordinate, screenShotDTO.getScreenshotCoordinate());
        assertEquals(timeLineItemId, screenShotDTO.getTimeLineItemId());
    }

}