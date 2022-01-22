package io.storydoc.server.ui.app;

import io.storydoc.server.storydoc.domain.ArtifactBlockCoordinate;
import io.storydoc.server.storydoc.domain.BlockId;
import io.storydoc.server.storydoc.domain.StoryDocId;
import io.storydoc.server.ui.domain.MockUIId;
import io.storydoc.server.ui.domain.ScreenShotCollectionId;
import io.storydoc.server.ui.domain.ScreenShotId;
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

    @GetMapping(value ="/screenshot", produces = MediaType.APPLICATION_JSON_VALUE)
    public ScreenShotDTO getScreenshot(String storyDocId, String blockId, String id) {
        ArtifactBlockCoordinate coordinate = ArtifactBlockCoordinate.builder()
                .storyDocId(new StoryDocId(storyDocId))
                .blockId(new BlockId(blockId))
                .build();
        return uiQueryService.getScreenshotDTO(coordinate, MockUIId.fromString(id));
    }

    @GetMapping(value="/mockui", produces = MediaType.APPLICATION_JSON_VALUE)
    public MockUIDTO getMockUI(String storyDocId, String blockId, String id) {
        ArtifactBlockCoordinate coordinate = ArtifactBlockCoordinate.builder()
                .storyDocId(new StoryDocId(storyDocId))
                .blockId(new BlockId(blockId))
                .build();
        return uiQueryService.getMockUIDTO(coordinate, new MockUIId(id));
    }

    @PostMapping(value = "/uiscenario", produces = MediaType.APPLICATION_JSON_VALUE)
    public MockUIId createMockUI(String storyDocId, String blockId, String name) {
        ArtifactBlockCoordinate coordinate = ArtifactBlockCoordinate.builder()
                .storyDocId(new StoryDocId(storyDocId))
                .blockId(new BlockId(blockId))
                .build();
        return uiService.createMockUI(coordinate, name);
    }

    @PostMapping(value = "/uiscenarioscreenshot", produces = MediaType.APPLICATION_JSON_VALUE)
    public void addScreenshotToUISceanrio(String storyDocId, String blockId, String mockUiId, String screenshotId) {
        ArtifactBlockCoordinate coordinate = ArtifactBlockCoordinate.builder()
                .storyDocId(new StoryDocId(storyDocId))
                .blockId(new BlockId(blockId))
                .build();
        uiService.addScreenShot(coordinate, MockUIId.fromString(mockUiId), ScreenShotId.fromString(screenshotId));
    }


    @PostMapping(value = "/screenshotcollection", produces = MediaType.APPLICATION_JSON_VALUE)
    public ScreenShotCollectionId createScreenShotCollection(String storyDocId, String blockId, String name) {
        ArtifactBlockCoordinate coordinate = ArtifactBlockCoordinate.builder()
                .storyDocId(new StoryDocId(storyDocId))
                .blockId(new BlockId(blockId))
                .build();
        return uiService.createScreenShotCollection(coordinate, name);
    }

    @GetMapping(value="/screenshotcollection", produces = MediaType.APPLICATION_JSON_VALUE)
    public ScreenShotCollectionDTO getScreenShotCollection(String storyDocId, String blockId, String id) {
        ArtifactBlockCoordinate coordinate = ArtifactBlockCoordinate.builder()
                .storyDocId(new StoryDocId(storyDocId))
                .blockId(new BlockId(blockId))
                .build();
        return uiQueryService.getScreenShotCollection(coordinate, ScreenShotCollectionId.fromString(id));
    }

    @PostMapping(value = "/screenshot", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @SneakyThrows
    public ScreenShotId addScreenshotToCollection(MultipartFile file, String storyDocId, String blockId, String screenshotCollectionId, String name) {
        ArtifactBlockCoordinate coordinate = ArtifactBlockCoordinate.builder()
                .storyDocId(new StoryDocId(storyDocId))
                .blockId(new BlockId(blockId))
                .build();
        return uiService.uploadScreenShotToCollection(coordinate, ScreenShotCollectionId.fromString(screenshotCollectionId), file.getInputStream(), name);
    }

    @SneakyThrows
    @GetMapping("/screenshot/{storyDocId}/{blockId}/{screenshotCollectionId}/{screenshotId}")
    public void downloadScreenshot(HttpServletResponse httpServletResponse, @PathVariable("storyDocId") String storyDocId, @PathVariable("blockId") String blockId, @PathVariable("screenshotCollectionId") String screenshotCollectionId, @PathVariable("screenshotId") String screenshotId)  {
        ArtifactBlockCoordinate coordinate = ArtifactBlockCoordinate.builder()
                .storyDocId(new StoryDocId(storyDocId))
                .blockId(new BlockId(blockId))
                .build();
        InputStream inputStream = uiQueryService.getScreenshot(coordinate, ScreenShotCollectionId.fromString(screenshotCollectionId), ScreenShotId.fromString(screenshotId));
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();
        inputStream.transferTo(outputStream);
        outputStream.flush();
    }


}
