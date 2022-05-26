package io.storydoc.server.ui.app;

import io.storydoc.server.TestBase;
import io.storydoc.server.storydoc.StoryDocTestFixture;
import io.storydoc.server.storydoc.app.StoryDocQueryService;
import io.storydoc.server.storydoc.app.StoryDocService;
import io.storydoc.server.storydoc.app.dto.ArtifactDTO;
import io.storydoc.server.storydoc.app.dto.BlockDTO;
import io.storydoc.server.storydoc.app.dto.StoryDocDTO;
import io.storydoc.server.storydoc.domain.BlockCoordinate;
import io.storydoc.server.storydoc.domain.BlockId;
import io.storydoc.server.storydoc.domain.StoryDocId;
import io.storydoc.server.timeline.app.TimeLineTestUtils;
import io.storydoc.server.timeline.domain.TimeLineCoordinate;
import io.storydoc.server.timeline.domain.TimeLineItemId;
import io.storydoc.server.timeline.domain.TimeLineModelCoordinate;
import io.storydoc.server.ui.domain.*;
import io.storydoc.server.workspace.WorkspaceTestFixture;
import io.storydoc.server.workspace.domain.WorkspaceException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UIServiceTest extends TestBase {

    @Autowired
    private StoryDocTestFixture storyDocTestFixture;

    @Autowired
    private TimeLineTestUtils timeLineTestUtils;

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
    private WorkspaceTestFixture workspaceTestFixture;

    @Autowired
    private UITestFixture uiTestFixture;

    @Test
    public void createUIScenario() {
        // given a storydoc with a artifact block
        BlockCoordinate coordinate = storyDocTestFixture.create_storydoc_with_artifact_block();
        StoryDocId storyDocId = coordinate.getStoryDocId();

        // when I create a uiscenario artifact
        String uiscenario_artifact_name = "uiscenario artifact";
        UIScenarioId uiId = uiService.createUIScenario(coordinate, uiscenario_artifact_name);
        UIScenarioCoordinate scenarioCoordinate = UIScenarioCoordinate.of(coordinate, uiId);
        assertNotNull(uiId);

        workspaceTestFixture.logFolderStructure("after creating mock ui artifact ");
        workspaceTestFixture.logResourceContent("storydoc", storyDocQueryService.getDocument(storyDocId).getUrn());
        workspaceTestFixture.logResourceContent("mock ui ", uiStorage.getUIScenarioUrn(scenarioCoordinate));

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
        BlockCoordinate coordinate = BlockCoordinate.builder()
                .storyDocId(storyDocId)
                .blockId(blockId)
                .build();
        // when I create a ScreenshotCollection artifact
        String screenshots_artifact_name = "screenshots";
        ScreenShotCollectionId id = uiService.createScreenShotCollection(coordinate, screenshots_artifact_name);
        assertNotNull(id);

        workspaceTestFixture.logFolderStructure("after creating mock ui artifact ");
        workspaceTestFixture.logResourceContent("storydoc", storyDocQueryService.getDocument(storyDocId).getUrn());

    }

    @Test
    public void addScreenShotToCollection() throws WorkspaceException {
        // given  a storydoc with an artifact block
        // given a storydoc with an artifact paragraph
        BlockCoordinate blockCoordinate = storyDocTestFixture.create_storydoc_with_artifact_block();

        // given a screenshot collection artifact
        String screenshots_artifact_name = "screenshots";
        ScreenShotCollectionId screenShotCollectionId = uiService.createScreenShotCollection(blockCoordinate, screenshots_artifact_name);
        ScreenshotCollectionCoordinate collectionCoordinate = ScreenshotCollectionCoordinate.of(blockCoordinate, screenShotCollectionId);

        // given an inputstream of a screenshot
        InputStream inputStream = this.getClass().getResourceAsStream("dummy-image-10x10.png");

        // when I upload a screenshot to the collection
        String screenshot_name = "screenshot";
        ScreenShotId screenShotId = uiService.uploadScreenShotToCollection(collectionCoordinate, inputStream, screenshot_name);

        workspaceTestFixture.logFolderStructure("after uploading screenshot");
        workspaceTestFixture.logResourceContent("storydoc", storyDocQueryService.getDocument(blockCoordinate.getStoryDocId()).getUrn());

        // then I can find it in the list of screenshots in the collection

        ScreenShotCollectionDTO dto = uiQueryService.getScreenShotCollection(collectionCoordinate);
        assertNotNull(dto);
        assertEquals(1, dto.getScreenShots().size());

        ScreenShotDTO screenShotDTO = dto.getScreenShots().get(0);
        assertEquals(screenshot_name, screenShotDTO.getName());
        assertEquals(screenShotId, screenShotDTO.getId());

        // and then I can download it
        InputStream  inputStream2 = uiQueryService.getScreenshot(collectionCoordinate, screenShotId);
        workspaceTestFixture.logBinaryInputstream(inputStream2);

    }

    @Test
    public void removeScreenShotFromCollection() throws WorkspaceException {
        // given  a storydoc with an artifact block
        // given a storydoc with an artifact paragraph
        BlockCoordinate blockCoordinate = storyDocTestFixture.create_storydoc_with_artifact_block();

        // given a screenshot collection artifact
        String screenshots_artifact_name = "screenshots";
        ScreenShotCollectionId screenShotCollectionId = uiService.createScreenShotCollection(blockCoordinate, screenshots_artifact_name);
        ScreenshotCollectionCoordinate collectionCoordinate = ScreenshotCollectionCoordinate.of(blockCoordinate, screenShotCollectionId);

        // given a screenshot was added to the collection
        InputStream inputStream = this.getClass().getResourceAsStream("dummy-image-10x10.png");
        String screenshot_name = "screenshot";
        ScreenShotId screenShotId = uiService.uploadScreenShotToCollection(collectionCoordinate, inputStream, screenshot_name);

        workspaceTestFixture.logFolderStructure("before removing screenshot");
        workspaceTestFixture.logResourceContent("storydoc", storyDocQueryService.getDocument(blockCoordinate.getStoryDocId()).getUrn());

        // when I remove the screenshot from the collection
        uiService.removeScreenShotFromCollection(ScreenshotCoordinate.of(collectionCoordinate, screenShotId));

        workspaceTestFixture.logFolderStructure("after removing screenshot");
        workspaceTestFixture.logResourceContent("storydoc", storyDocQueryService.getDocument(blockCoordinate.getStoryDocId()).getUrn());

        // then the screenshot is no longer part of the collection
        ScreenShotCollectionDTO dto = uiQueryService.getScreenShotCollection(collectionCoordinate);
        assertNotNull(dto);
        assertEquals(0, dto.getScreenShots().size());


    }

    @Test
    public void renameScreenShot() throws WorkspaceException {
        // given  a storydoc with an artifact block
        // given a storydoc with an artifact paragraph
        BlockCoordinate blockCoordinate = storyDocTestFixture.create_storydoc_with_artifact_block();

        // given a screenshot collection artifact
        String screenshots_artifact_name = "screenshots";
        ScreenShotCollectionId screenShotCollectionId = uiService.createScreenShotCollection(blockCoordinate, screenshots_artifact_name);
        ScreenshotCollectionCoordinate collectionCoordinate = ScreenshotCollectionCoordinate.of(blockCoordinate, screenShotCollectionId);

        // given a screenshot was added to the collection
        InputStream inputStream = this.getClass().getResourceAsStream("dummy-image-10x10.png");
        String screenshot_name_before = "screenshot_name_before";
        ScreenShotId screenShotId = uiService.uploadScreenShotToCollection(collectionCoordinate, inputStream, screenshot_name_before);

        workspaceTestFixture.logFolderStructure("before renaming screenshot");
        workspaceTestFixture.logResourceContent("storydoc", storyDocQueryService.getDocument(blockCoordinate.getStoryDocId()).getUrn());

        // when I rename the screenshot
        String screenshot_name_after = "screenshot__name_after";
        uiService.renameScreenShotInCollection(ScreenshotCoordinate.of(collectionCoordinate, screenShotId), screenshot_name_after);

        workspaceTestFixture.logFolderStructure("after renaming screenshot");
        workspaceTestFixture.logResourceContent("storydoc", storyDocQueryService.getDocument(blockCoordinate.getStoryDocId()).getUrn());

        // then the screenshot is renamed
        ScreenShotCollectionDTO dto = uiQueryService.getScreenShotCollection(collectionCoordinate);
        ScreenShotDTO screenShotDTO = dto.getScreenShots().get(0);
        assertEquals(screenshot_name_after, screenShotDTO.getName());


    }


    @Test
    public void UIScenario_Has_Default_Associated_Snapshot_Collection () {
        // given a storydoc with an artifact paragraph
        BlockCoordinate blockCoordinate = storyDocTestFixture.create_storydoc_with_artifact_block();
        // given a screenshot collection artifact in that paragraph
        ScreenshotCollectionCoordinate collectionCoordinate = uiTestFixture.createScreenshotCollection(blockCoordinate);
        // given  a screenshot is uploaded to the collection
        ScreenShotId screenShotId = uiTestFixture.upoadScreenShotToCollection(collectionCoordinate);

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

        workspaceTestFixture.logFolderStructure("after uploading screenshot");
        workspaceTestFixture.logResourceContent("storydoc", storyDocQueryService.getDocument(blockCoordinate.getStoryDocId()).getUrn());

    }

    @Test
    public void associate_timeline_to_ui_scenario() {

        // given a storydoc with an artifact paragraph
        BlockCoordinate blockCoordinate = storyDocTestFixture.create_storydoc_with_artifact_block();

        // given a ui scenario
        UIScenarioCoordinate scenarioCoordinate = uiTestFixture.createUIScenario(blockCoordinate);

        // given a timeline model
        TimeLineModelCoordinate timeLineModelCoordinate = timeLineTestUtils.createTimeLineModel(blockCoordinate);
        TimeLineCoordinate defaultTimeLineCoordinate = timeLineTestUtils.getDefaultTimeLine(timeLineModelCoordinate);

        // when I associate the timeline with the UI scenario
        uiService.setTimeLineModelForUIScenario(scenarioCoordinate, timeLineModelCoordinate);

        // I can find the associated timeline
        UIScenarioDTO dto = uiQueryService.getUIScenario(scenarioCoordinate);
        assertEquals(timeLineModelCoordinate, dto.getTimeLineModelCoordinate());

        workspaceTestFixture.logFolderStructure("after setting timeline");
        workspaceTestFixture.logResourceContent("storydoc", storyDocQueryService.getDocument(blockCoordinate.getStoryDocId()).getUrn());


    }

    @Test
    public void add_screenshot_to_ui_scenario() {

        // given a storydoc with an artifact paragraph
        BlockCoordinate blockCoordinate = storyDocTestFixture.create_storydoc_with_artifact_block();

        // given a screenshot collection artifact in that paragraph
        ScreenshotCollectionCoordinate collectionCoordinate = uiTestFixture.createScreenshotCollection(blockCoordinate);

        // given  a screenshot is uploaded to the collection
        ScreenShotId screenShotId = uiTestFixture.upoadScreenShotToCollection(collectionCoordinate);

        // given a uiscenario artifact in the same paragraph
        UIScenarioCoordinate scenarioCoordinate =  uiTestFixture.createUIScenario(blockCoordinate);

        // given a timeline model in the same paragraph
        TimeLineModelCoordinate timeLineModelCoordinate = timeLineTestUtils.createTimeLineModel(blockCoordinate);
        TimeLineCoordinate defaultTimeLineCoordinate = timeLineTestUtils.getDefaultTimeLine(timeLineModelCoordinate);

        // given the timeline model is associated with the UI scenario
        uiService.setTimeLineModelForUIScenario(scenarioCoordinate, timeLineModelCoordinate);

        // given a timeLine item is added to the default timeline
        TimeLineItemId timeLineItemId = timeLineTestUtils.addItemToDefaultTimeLine(timeLineModelCoordinate);

        // when I add the screenshot to the UI scenario

        ScreenshotCoordinate screenshotCoordinate = ScreenshotCoordinate.of(collectionCoordinate, screenShotId);
        uiService.addScreenShotToUIScenario(scenarioCoordinate, screenshotCoordinate, timeLineItemId);

        // then the UI scenario has the screenshot
        UIScenarioDTO mockUIDTO = uiQueryService.getUIScenario(scenarioCoordinate);

        assertEquals(1, mockUIDTO.getScreenshots().size());
        ScreenShotTimeLineItemDTO screenShotDTO = mockUIDTO.getScreenshots().get(0);
        assertEquals(screenshotCoordinate, screenShotDTO.getScreenshotCoordinate());
        assertEquals(timeLineItemId, screenShotDTO.getTimeLineItemId());
    }

    @Test
    public void update_screenshot_in_ui_scenario() {
        // given a storydoc with an artifact paragraph
        BlockCoordinate blockCoordinate = storyDocTestFixture.create_storydoc_with_artifact_block();

        // given a screenshot collection artifact in that paragraph
        ScreenshotCollectionCoordinate collectionCoordinate = uiTestFixture.createScreenshotCollection(blockCoordinate);

        // given  a screenshot is uploaded to the collection
        ScreenShotId screenShotId = uiTestFixture.upoadScreenShotToCollection(collectionCoordinate);
        ScreenshotCoordinate screenshotCoordinate = ScreenshotCoordinate.of(collectionCoordinate, screenShotId);

        // given a uiscenario artifact in the same paragraph
        UIScenarioCoordinate scenarioCoordinate =  uiTestFixture.createUIScenario(blockCoordinate);

        // given a timeline model in the same paragraph
        TimeLineModelCoordinate timeLineModelCoordinate = timeLineTestUtils.createTimeLineModel(blockCoordinate);
        TimeLineCoordinate defaultTimeLineCoordinate = timeLineTestUtils.getDefaultTimeLine(timeLineModelCoordinate);

        // given I the timeline is associated with the UI scenario
        uiService.setTimeLineModelForUIScenario(scenarioCoordinate, timeLineModelCoordinate);

        // given a timeLine item is added to the default timeline
        TimeLineItemId timeLineItemId = timeLineTestUtils.addItemToDefaultTimeLine(timeLineModelCoordinate);

        // given the screenshot is added to the UI scenario
        uiService.addScreenShotToUIScenario(scenarioCoordinate, screenshotCoordinate, timeLineItemId);

        // given  another screenshot is uploaded to the collection
        ScreenShotId screenShotId2 = uiTestFixture.upoadScreenShotToCollection(collectionCoordinate);
        ScreenshotCoordinate screenshotCoordinate2 = ScreenshotCoordinate.of(collectionCoordinate, screenShotId2);

        // when I add the screenshot to the same timeline item in the UI scenario
        uiService.addScreenShotToUIScenario(scenarioCoordinate, screenshotCoordinate2, timeLineItemId);

        // then the second  screenshots replaces the first in the UI scenario
        UIScenarioDTO mockUIDTO = uiQueryService.getUIScenario(scenarioCoordinate);

        assertEquals(1, mockUIDTO.getScreenshots().size());
        ScreenShotTimeLineItemDTO screenShotDTO = mockUIDTO.getScreenshots().get(0);
        assertEquals(screenshotCoordinate2, screenShotDTO.getScreenshotCoordinate());
        assertEquals(timeLineItemId, screenShotDTO.getTimeLineItemId());

    }

    @Test
    public void remove_screenshot_from_ui_scenario() {
        // given a storydoc with an artifact paragraph
        BlockCoordinate blockCoordinate = storyDocTestFixture.create_storydoc_with_artifact_block();

        // given a screenshot collection artifact in that paragraph
        ScreenshotCollectionCoordinate collectionCoordinate = uiTestFixture.createScreenshotCollection(blockCoordinate);

        // given  a screenshot is uploaded to the collection
        ScreenShotId screenShotId = uiTestFixture.upoadScreenShotToCollection(collectionCoordinate);
        ScreenshotCoordinate screenshotCoordinate = ScreenshotCoordinate.of(collectionCoordinate, screenShotId);

        // given a uiscenario artifact in the same paragraph
        UIScenarioCoordinate scenarioCoordinate =  uiTestFixture.createUIScenario(blockCoordinate);

        // given a timeline model in the same paragraph
        TimeLineModelCoordinate timeLineModelCoordinate = timeLineTestUtils.createTimeLineModel(blockCoordinate);
        TimeLineCoordinate defaultTimeLineCoordinate = timeLineTestUtils.getDefaultTimeLine(timeLineModelCoordinate);

        // given the timeline model is associated with the UI scenario
        uiService.setTimeLineModelForUIScenario(scenarioCoordinate, timeLineModelCoordinate);

        // given a timeLine item is added to the default timeline
        TimeLineItemId timeLineItemId = timeLineTestUtils.addItemToDefaultTimeLine(timeLineModelCoordinate);

        // given the screenshot is added to the UI scenario
        uiService.addScreenShotToUIScenario(scenarioCoordinate, screenshotCoordinate, timeLineItemId);

        // when I delete the screenshot from hte UI scenario
        uiService.removeScreenshotFromUIScenario(scenarioCoordinate, timeLineItemId);

        // then the UI scenario no longer has the screenshot
        UIScenarioDTO mockUIDTO = uiQueryService.getUIScenario(scenarioCoordinate);

        assertEquals(0, mockUIDTO.getScreenshots().size());

    }

}