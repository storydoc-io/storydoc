package io.storydoc.server.ui.app;

import io.storydoc.server.storydoc.domain.ArtifactBlockCoordinate;
import io.storydoc.server.storydoc.domain.BlockId;
import io.storydoc.server.storydoc.domain.StoryDocId;
import io.storydoc.server.timeline.domain.*;
import io.storydoc.server.ui.domain.*;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

@RestController
@RequestMapping("/api/ui")
public class UIRestController {

    private final UIService uiService;

    private final UIQueryService uiQueryService;

    public UIRestController(UIService uiService, UIQueryService uiQueryService) {
        this.uiService = uiService;
        this.uiQueryService = uiQueryService;
    }

    @GetMapping(value="/uiscenario", produces = MediaType.APPLICATION_JSON_VALUE)
    public UIScenarioDTO getMockUI(String storyDocId, String blockId, String id) {
        ArtifactBlockCoordinate blockCoordinate = ArtifactBlockCoordinate.of(new StoryDocId(storyDocId), new BlockId(blockId));
        UIScenarioCoordinate scenarioCoordinate = UIScenarioCoordinate.of(blockCoordinate,  new UIScenarioId(id));
        return uiQueryService.getUIScenario(scenarioCoordinate);
    }

    @PostMapping(value = "/uiscenario", produces = MediaType.APPLICATION_JSON_VALUE)
    public UIScenarioId createUIScenario(String storyDocId, String blockId, String name) {
        ArtifactBlockCoordinate coordinate = ArtifactBlockCoordinate.of(new StoryDocId(storyDocId), new BlockId(blockId));
        return uiService.createUIScenario(coordinate, name);
    }

    @PostMapping(value = "/uiscenariotimeline", produces = MediaType.APPLICATION_JSON_VALUE)
    public void setUIScenarioTimeLine(String storyDocId, String blockId, String uiScenarioId, String timeLineModelStoryDocId, String timeLineModelBlockId, String timeLineModelId, String timeLineId ) {
        ArtifactBlockCoordinate uiScenarioBlockCoordinate = ArtifactBlockCoordinate.of(new StoryDocId(storyDocId), new BlockId(blockId));
        UIScenarioCoordinate scenarioCoordinate = UIScenarioCoordinate.of(uiScenarioBlockCoordinate, UIScenarioId.fromString(uiScenarioId));

        ArtifactBlockCoordinate timeLineModelBlockCoordinate = ArtifactBlockCoordinate.of(StoryDocId.fromString(timeLineModelStoryDocId), BlockId.fromString(timeLineModelBlockId));
        TimeLineModelCoordinate timeLineModelCoordinate = TimeLineModelCoordinate.of(timeLineModelBlockCoordinate, TimeLineModelId.fromString(timeLineModelId));
        TimeLineCoordinate timeLineCoordinate = TimeLineCoordinate.of(timeLineModelCoordinate, TimeLineId.fromString(timeLineId));

        uiService.setTimeLineForUIScenario(scenarioCoordinate,timeLineCoordinate);
    }


    @PostMapping(value = "/uiscenarioscreenshot", produces = MediaType.APPLICATION_JSON_VALUE)
    public void addScreenshotToUIScenario(String storyDocId, String blockId, String uiScenarioId, String screenshotStoryDocId, String screenshotBlockId, String screenshotCollectionId, String screenshotId, String timeLineItemId) {
        ArtifactBlockCoordinate uiScenarioBlockCoordinate = ArtifactBlockCoordinate.of(new StoryDocId(storyDocId), new BlockId(blockId));
        UIScenarioCoordinate scenarioCoordinate = UIScenarioCoordinate.of(uiScenarioBlockCoordinate, UIScenarioId.fromString(uiScenarioId));

        ArtifactBlockCoordinate screenshotBlockCoordinate = ArtifactBlockCoordinate.of(StoryDocId.fromString(screenshotStoryDocId), BlockId.fromString(screenshotBlockId));
        ScreenshotCoordinate screenshotCoordinate = ScreenshotCoordinate.of(screenshotBlockCoordinate, ScreenShotCollectionId.fromString(screenshotCollectionId), ScreenShotId.fromString(screenshotId));

        uiService.addScreenShotToUIScenario(scenarioCoordinate, screenshotCoordinate, TimeLineItemId.fromString(timeLineItemId));
    }


    @PostMapping(value = "/screenshotcollection", produces = MediaType.APPLICATION_JSON_VALUE)
    public ScreenShotCollectionId createScreenShotCollection(String storyDocId, String blockId, String name) {
        ArtifactBlockCoordinate coordinate = ArtifactBlockCoordinate.of(new StoryDocId(storyDocId), new BlockId(blockId));
        return uiService.createScreenShotCollection(coordinate, name);
    }

    @GetMapping(value="/screenshotcollection", produces = MediaType.APPLICATION_JSON_VALUE)
    public ScreenShotCollectionDTO getScreenShotCollection(String storyDocId, String blockId, String id) {
        ArtifactBlockCoordinate blockCoordinate = ArtifactBlockCoordinate.of(new StoryDocId(storyDocId), new BlockId(blockId));
        return uiQueryService.getScreenShotCollection(ScreenshotCollectionCoordinate.of(blockCoordinate, ScreenShotCollectionId.fromString(id)));
    }

    @PostMapping(value = "/screenshot", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @SneakyThrows
    public ScreenShotId addScreenshotToCollection(MultipartFile file, String storyDocId, String blockId, String screenshotCollectionId, String name) {
        ArtifactBlockCoordinate blockCoordinate = ArtifactBlockCoordinate.of(new StoryDocId(storyDocId), new BlockId(blockId));
        ScreenshotCollectionCoordinate collectionCoordinate = ScreenshotCollectionCoordinate.of(blockCoordinate, ScreenShotCollectionId.fromString(screenshotCollectionId));
        return uiService.uploadScreenShotToCollection(collectionCoordinate, file.getInputStream(), name);
    }

    @SneakyThrows
    @GetMapping("/screenshot/{storyDocId}/{blockId}/{screenshotCollectionId}/{screenshotId}")
    public void downloadScreenshot(HttpServletResponse httpServletResponse, @PathVariable("storyDocId") String storyDocId, @PathVariable("blockId") String blockId, @PathVariable("screenshotCollectionId") String screenshotCollectionId, @PathVariable("screenshotId") String screenshotId)  {
        ArtifactBlockCoordinate blockCoordinate = ArtifactBlockCoordinate.of(new StoryDocId(storyDocId), new BlockId(blockId));
        ScreenshotCollectionCoordinate collectionCoordinate = ScreenshotCollectionCoordinate.of(blockCoordinate, ScreenShotCollectionId.fromString(screenshotCollectionId));
        InputStream inputStream = uiQueryService.getScreenshot(collectionCoordinate, ScreenShotId.fromString(screenshotId));
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();
        inputStream.transferTo(outputStream);
        outputStream.flush();
    }


}
