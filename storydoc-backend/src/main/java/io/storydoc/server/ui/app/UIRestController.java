package io.storydoc.server.ui.app;

import io.storydoc.server.storydoc.domain.BlockCoordinate;
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
    public UIScenarioDTO getUIScenario(@RequestParam("storyDocId") StoryDocId storyDocId, @RequestParam("blockId") BlockId blockId, @RequestParam("id") UIScenarioId id) {
        UIScenarioCoordinate scenarioCoordinate = UIScenarioCoordinate.of(storyDocId, blockId,  id);
        return uiQueryService.getUIScenario(scenarioCoordinate);
    }

    @PostMapping(value = "/uiscenario", produces = MediaType.APPLICATION_JSON_VALUE)
    public UIScenarioCoordinate createUIScenario(@RequestParam("storyDocId") StoryDocId storyDocId, @RequestParam("blockId") BlockId blockId, @RequestParam("name") String name) {
        BlockCoordinate blockCoordinate = BlockCoordinate.of(storyDocId, blockId);
        UIScenarioId uiScenarioId = uiService.createUIScenario(blockCoordinate, name);
        return UIScenarioCoordinate.of(blockCoordinate, uiScenarioId);
    }

    @PostMapping(value = "/uiscenariotimelinemodel", produces = MediaType.APPLICATION_JSON_VALUE)
    public void setUIScenarioTimeLineModel(@RequestParam("storyDocId") StoryDocId storyDocId, @RequestParam("blockId") BlockId blockId, @RequestParam("uiScenarioId") UIScenarioId uiScenarioId,
                                      @RequestParam("timeLineModelStoryDocId") StoryDocId timeLineModelStoryDocId, @RequestParam("timeLineModelBlockId") BlockId timeLineModelBlockId, @RequestParam("timeLineModelId") TimeLineModelId timeLineModelId) {
        UIScenarioCoordinate scenarioCoordinate = UIScenarioCoordinate.of(storyDocId, blockId, uiScenarioId);
        TimeLineModelCoordinate timeLineModelCoordinate = TimeLineModelCoordinate.of(timeLineModelStoryDocId, timeLineModelBlockId, timeLineModelId);
        uiService.setTimeLineModelForUIScenario(scenarioCoordinate,timeLineModelCoordinate);
    }


    @PostMapping(value = "/uiscenarioscreenshot", produces = MediaType.APPLICATION_JSON_VALUE)
    public void addScreenshotToUIScenario(@RequestParam("storyDocId") StoryDocId storyDocId, @RequestParam("blockId") BlockId blockId, @RequestParam("uiScenarioId") UIScenarioId uiScenarioId,
                                          @RequestParam("screenshotStoryDocId") StoryDocId screenshotStoryDocId, @RequestParam("screenshotBlockId") BlockId screenshotBlockId, @RequestParam("screenshotCollectionId") ScreenShotCollectionId screenshotCollectionId, @RequestParam("screenshotId") ScreenShotId screenshotId,
                                          @RequestParam("timeLineItemId") TimeLineItemId timeLineItemId) {
        UIScenarioCoordinate scenarioCoordinate = UIScenarioCoordinate.of(storyDocId, blockId, uiScenarioId);
        ScreenshotCoordinate screenshotCoordinate = ScreenshotCoordinate.of(screenshotStoryDocId, screenshotBlockId, screenshotCollectionId, screenshotId);

        uiService.addScreenShotToUIScenario(scenarioCoordinate, screenshotCoordinate, timeLineItemId);
    }

    @PostMapping(value = "/screenshotcollection", produces = MediaType.APPLICATION_JSON_VALUE)
    public ScreenShotCollectionId createScreenShotCollection(@RequestParam("storyDocId") StoryDocId storyDocId, @RequestParam("blockId") BlockId blockId, String name) {
        BlockCoordinate coordinate = BlockCoordinate.of(storyDocId, blockId);
        return uiService.createScreenShotCollection(coordinate, name);
    }

    @GetMapping(value="/screenshotcollection", produces = MediaType.APPLICATION_JSON_VALUE)
    public ScreenShotCollectionDTO getScreenShotCollection(@RequestParam("storyDocId") StoryDocId storyDocId, @RequestParam("blockId") BlockId blockId, @RequestParam("id") ScreenShotCollectionId id) {
        return uiQueryService.getScreenShotCollection(ScreenshotCollectionCoordinate.of(storyDocId, blockId, id));
    }

    @PostMapping(value = "/screenshot", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @SneakyThrows
    public ScreenShotId addScreenshotToCollection(MultipartFile file, @RequestParam("storyDocId") StoryDocId storyDocId, @RequestParam("blockId") BlockId blockId, @RequestParam("screenshotCollectionId") ScreenShotCollectionId screenshotCollectionId, String name) {
        ScreenshotCollectionCoordinate collectionCoordinate = ScreenshotCollectionCoordinate.of(storyDocId, blockId, screenshotCollectionId);
        return uiService.uploadScreenShotToCollection(collectionCoordinate, file.getInputStream(), name);
    }

    @PutMapping(value = "/screenshot", produces = MediaType.APPLICATION_JSON_VALUE)
    @SneakyThrows
    public void renameScreenshotInCollection(@RequestParam("storyDocId") StoryDocId storyDocId, @RequestParam("blockId") BlockId blockId, @RequestParam("screenshotCollectionId") ScreenShotCollectionId screenshotCollectionId, @RequestParam("screenshotId") ScreenShotId screenShotId, String name) {
        ScreenshotCollectionCoordinate collectionCoordinate = ScreenshotCollectionCoordinate.of(storyDocId, blockId, screenshotCollectionId);
        uiService.renameScreenShotInCollection(ScreenshotCoordinate.of(collectionCoordinate, screenShotId), name);
    }

    @DeleteMapping(value = "/screenshot", produces = MediaType.APPLICATION_JSON_VALUE)
    @SneakyThrows
    public void removeScreenshotFromCollection(@RequestParam("storyDocId") StoryDocId storyDocId, @RequestParam("blockId") BlockId blockId, @RequestParam("screenshotCollectionId") ScreenShotCollectionId screenshotCollectionId, @RequestParam("screenshotId") ScreenShotId screenShotId) {
        ScreenshotCollectionCoordinate collectionCoordinate = ScreenshotCollectionCoordinate.of(storyDocId, blockId, screenshotCollectionId);
        uiService.removeScreenShotFromCollection(ScreenshotCoordinate.of(collectionCoordinate, screenShotId));
    }

    @SneakyThrows
    @GetMapping("/screenshot/{storyDocId}/{blockId}/{screenshotCollectionId}/{screenshotId}")
    public void downloadScreenshot(HttpServletResponse httpServletResponse, @PathVariable("storyDocId") StoryDocId storyDocId, @PathVariable("blockId") BlockId blockId, @PathVariable("screenshotCollectionId") ScreenShotCollectionId screenshotCollectionId, @PathVariable("screenshotId") ScreenShotId screenshotId)  {
        ScreenshotCollectionCoordinate collectionCoordinate = ScreenshotCollectionCoordinate.of(storyDocId, blockId, screenshotCollectionId);
        InputStream inputStream = uiQueryService.getScreenshot(collectionCoordinate, screenshotId);
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();
        inputStream.transferTo(outputStream);
        outputStream.flush();
    }


}
