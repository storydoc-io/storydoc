package io.storydoc.server.ui.app.screendesign;

import io.storydoc.server.storydoc.domain.BlockCoordinate;
import io.storydoc.server.storydoc.domain.BlockId;
import io.storydoc.server.storydoc.domain.StoryDocId;
import io.storydoc.server.ui.domain.screendesign.SDComponentId;
import io.storydoc.server.ui.domain.screendesign.ScreenDesignCoordinate;
import io.storydoc.server.ui.domain.screendesign.ScreenDesignId;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ui/screendesign")
public class ScreenDesignRestController {

    private final ScreenDesignService screenDesignService;

    private final ScreenDesignQueryService screenDesignQueryService;

    public ScreenDesignRestController(ScreenDesignService screenDesignService, ScreenDesignQueryService screenDesignQueryService) {
        this.screenDesignService = screenDesignService;
        this.screenDesignQueryService = screenDesignQueryService;
    }

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ScreenDesignCoordinate createScreenDesign(@RequestParam("storyDocId") StoryDocId storyDocId, @RequestParam("blockId") BlockId blockId, @RequestParam("name") String name) {
        BlockCoordinate blockCoordinate = BlockCoordinate.of(storyDocId, blockId);
        return screenDesignService.createScreenDesign(blockCoordinate, name);
    }

    @PostMapping(value = "/component", produces = MediaType.APPLICATION_JSON_VALUE)
    public SDComponentId createComponent(@RequestParam("storyDocId") StoryDocId storyDocId, @RequestParam("blockId") BlockId blockId,
             @RequestParam("screenDesignId") ScreenDesignId screenDesignId, @RequestParam("containerId") SDComponentId containerId, @RequestParam("type") String type,
             @RequestParam("x") int x, @RequestParam("y") int y) {
        BlockCoordinate blockCoordinate = BlockCoordinate.of(storyDocId, blockId);
        ScreenDesignCoordinate screenDesignCoordinate = ScreenDesignCoordinate.of(blockCoordinate, screenDesignId);
        return screenDesignService.addComponent(screenDesignCoordinate, containerId, type, x, y);
    }

    @GetMapping(value="/palette", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ComponentDescriptorDTO> getComponentPalette() {
        return screenDesignQueryService.getComponentPalette();
    }

    @GetMapping(value="/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ScreenDesignDTO getScreenDesign(@RequestParam("storyDocId") StoryDocId storyDocId, @RequestParam("blockId") BlockId blockId
            , @RequestParam("screenDesignId") ScreenDesignId screenDesignId) {
        BlockCoordinate blockCoordinate = BlockCoordinate.of(storyDocId, blockId);
        ScreenDesignCoordinate screenDesignCoordinate = ScreenDesignCoordinate.of(blockCoordinate, screenDesignId);
        return screenDesignQueryService.getScreenDesign(screenDesignCoordinate);
    }

}
