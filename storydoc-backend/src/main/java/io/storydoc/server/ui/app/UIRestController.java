package io.storydoc.server.ui.app;

import io.storydoc.server.storydoc.domain.ArtifactBlockCoordinate;
import io.storydoc.server.storydoc.domain.BlockId;
import io.storydoc.server.storydoc.domain.StoryDocId;
import io.storydoc.server.ui.domain.MockUIId;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ui")
public class UIRestController {

    private final UIQueryService uiQueryService;

    public UIRestController(UIQueryService uiQueryService) {
        this.uiQueryService = uiQueryService;
    }

    @GetMapping(value ="/screenshot", produces = MediaType.APPLICATION_JSON_VALUE)
    public ScreenshotDTO getMockUI(String storyDocId, String blockId, String id) {
        ArtifactBlockCoordinate coordinate = ArtifactBlockCoordinate.builder()
                .storyDocId(new StoryDocId(storyDocId))
                .blockId(new BlockId(blockId))
                .build();
        return uiQueryService.getScreenshotDTO(coordinate, MockUIId.fromString(id));
    }

}
